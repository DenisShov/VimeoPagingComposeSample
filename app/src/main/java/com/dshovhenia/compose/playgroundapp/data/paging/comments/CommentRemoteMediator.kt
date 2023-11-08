package com.dshovhenia.compose.playgroundapp.data.paging.comments

import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.dshovhenia.compose.playgroundapp.data.local.db.helper.CommentDbHelper
import com.dshovhenia.compose.playgroundapp.data.mapper.comment.toCachedComment
import com.dshovhenia.compose.playgroundapp.data.local.db.model.comment.RelationsComment
import com.dshovhenia.compose.playgroundapp.data.remote.model.Collection
import com.dshovhenia.compose.playgroundapp.data.remote.model.comment.Comment
import com.dshovhenia.compose.playgroundapp.data.remote.service.VimeoApiService
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

@OptIn(androidx.paging.ExperimentalPagingApi::class)
class CommentRemoteMediator(
    private val initialUri: String,
    private val commentDbHelper: CommentDbHelper,
    private val service: VimeoApiService,
    private val perPageCount: Int,
) : RemoteMediator<Int, RelationsComment>() {

    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, RelationsComment>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.comment?.nextPage

                    // Explicitly check if the page key is null when
                    // appending, since null is only valid for initial load.
                    // If you receive null for APPEND, that means you have
                    // reached the end of pagination and there are no more
                    // items to load.
                    if (remoteKey == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }

                    remoteKey
                }
            }

            val response = if (loadKey == null) {
                Timber.i("getComments. initialUri: %s", initialUri)
                service.getComments(initialUri, null, INITIAL_PAGE_NUMBER, perPageCount)
            } else {
                Timber.i("getComments. loadKey: %s", loadKey)
                service.getComments(loadKey, null, null, null)
            }

            if (loadType == LoadType.REFRESH) {
                Timber.i("clear comments. loadType: %s", loadType)
                commentDbHelper.clear()
            }

            if (response.isSuccessful) {
                val collection = response.body()
                if (collection != null && collection.data.isNotEmpty()) {
                    // Store the loaded data and the next key in transaction, so that
                    // they're always consistent.
                    val videos = addLinkToNextPage(collection)
                    commentDbHelper.insertComments(videos.map { it.toCachedComment() })
                } else {
                    Timber.i("No data. Response: %s", response)
                }
            }

            val endOfPaginationReached = response.body()?.data.isNullOrEmpty()
            Timber.i("End of pagination reached = %s", endOfPaginationReached)
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            e.printStackTrace()
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            e.printStackTrace()
            MediatorResult.Error(e)
        }
    }

    private fun addLinkToNextPage(collection: Collection<Comment>) = collection.data.map {
        it.nextPage = collection.paging?.next ?: ""
        it
    }

    companion object {
        private const val INITIAL_PAGE_NUMBER = 1
    }
}
