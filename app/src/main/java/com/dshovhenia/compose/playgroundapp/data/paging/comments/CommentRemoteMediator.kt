package com.dshovhenia.compose.playgroundapp.data.paging.comments

import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.dshovhenia.compose.playgroundapp.data.local.db.helper.CommentDbHelper
import com.dshovhenia.compose.playgroundapp.data.local.db.model.comment.RelationsComment
import com.dshovhenia.compose.playgroundapp.data.mapper.comment.toCachedComment
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
                    val comment = state.lastItemOrNull()?.comment
                    val nextPage = comment?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = comment != null
                        )
                    nextPage
                }
            }

            if (loadType == LoadType.REFRESH) {
                commentDbHelper.clear()
            }

            val response = if (loadKey == null) {
                service.getComments(initialUri, INITIAL_PAGE_NUMBER, perPageCount)
            } else {
                service.getComments(loadKey, null, null)
            }

            val collection = response.body()

            if (response.isSuccessful) {
                if (collection != null && collection.data.isNotEmpty()) {
                    // Store the loaded data and the next key in transaction, so that
                    // they're always consistent.
                    val comments = addLinkToNextPage(collection)
                    val commentsToSave = comments.map { it.toCachedComment() }
                    commentDbHelper.insertComments(commentsToSave)
                } else {
                    Timber.i("No data. Response: %s", response)
                }
            }

            val endOfPaginationReached = collection?.data.isNullOrEmpty()
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            e.printStackTrace()
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            e.printStackTrace()
            MediatorResult.Error(e)
        }
    }

    private fun addLinkToNextPage(collection: Collection<Comment>) =
        collection.data.map {
            it.nextPage = collection.paging?.next
            it
        }

    companion object {
        private const val INITIAL_PAGE_NUMBER = 1
    }
}
