package com.dshovhenia.compose.playgroundapp.paging.comments

import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.dshovhenia.compose.playgroundapp.data.cache.helper.CommentDbHelper
import com.dshovhenia.compose.playgroundapp.data.cache.mapper.comment.toCachedComment
import com.dshovhenia.compose.playgroundapp.data.cache.model.comment.RelationsComment
import com.dshovhenia.compose.playgroundapp.data.model.Collection
import com.dshovhenia.compose.playgroundapp.data.model.comment.Comment
import com.dshovhenia.compose.playgroundapp.data.remote.service.VimeoApiService
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

@OptIn(androidx.paging.ExperimentalPagingApi::class)
class CommentRemoteMediator(
    private val initialUri: String,
    private val commentDbHelper: CommentDbHelper,
    private val service: VimeoApiService
) : RemoteMediator<Int, RelationsComment>() {

    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, RelationsComment>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = state.pages.lastOrNull()?.data?.lastOrNull()?.comment?.nextPage

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
                service.getComments(initialUri, null, INITIAL_PAGE_NUMBER, PER_PAGE_COUNT)
            } else {
                service.getComments(loadKey, null, null, null)
            }

            if (loadType == LoadType.REFRESH) {
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

            MediatorResult.Success(endOfPaginationReached = response.body()?.data.isNullOrEmpty())
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
        private const val PER_PAGE_COUNT = 20
    }

}
