package com.dshovhenia.vimeo.paging.compose.sample.data.paging.videos

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.dshovhenia.vimeo.paging.compose.sample.data.local.db.helper.VideoDbHelper
import com.dshovhenia.vimeo.paging.compose.sample.data.mapper.video.toCachedVideo
import com.dshovhenia.vimeo.paging.compose.sample.data.local.db.model.video.RelationsVideo
import com.dshovhenia.vimeo.paging.compose.sample.data.remote.model.Collection
import com.dshovhenia.vimeo.paging.compose.sample.data.remote.model.video.Video
import com.dshovhenia.vimeo.paging.compose.sample.data.remote.service.VimeoApiService
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class VideoRemoteMediator(
    private val initialUri: String,
    private val keyword: String?,
    private val videoDbHelper: VideoDbHelper,
    private val service: VimeoApiService,
    private val perPageCount: Int
) : RemoteMediator<Int, RelationsVideo>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RelationsVideo>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val video = state.lastItemOrNull()?.video
                    val nextPage = video?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = video != null
                        )
                    nextPage
                }
            }

            if (loadType == LoadType.REFRESH) {
                videoDbHelper.clear()
            }

            val response = if (loadKey == null) {
                service.getVideos(initialUri, keyword, INITIAL_PAGE_NUMBER, perPageCount)
            } else {
                service.getVideos(loadKey, null, null, null)
            }

            if (response.isSuccessful) {
                val collection = response.body()
                if (collection != null && collection.data.isNotEmpty()) {
                    // Store loaded data, and next key in transaction, so that
                    // they're always consistent.
                    val videos = addLinkToNextPage(collection)
                    videoDbHelper.insertVideos(videos.map { it.toCachedVideo() })
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

    private fun addLinkToNextPage(collection: Collection<Video>) =
        collection.data.map {
            it.nextPage = collection.paging?.next
            it
        }

    companion object {
        private const val INITIAL_PAGE_NUMBER = 1
    }

}
