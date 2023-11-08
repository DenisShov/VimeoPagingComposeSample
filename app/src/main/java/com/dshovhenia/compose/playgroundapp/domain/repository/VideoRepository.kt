package com.dshovhenia.compose.playgroundapp.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.dshovhenia.compose.playgroundapp.data.local.db.VimeoDatabase
import com.dshovhenia.compose.playgroundapp.data.local.db.helper.VideoDbHelper
import com.dshovhenia.compose.playgroundapp.data.local.db.model.video.RelationsVideo
import com.dshovhenia.compose.playgroundapp.data.paging.videos.VideoRemoteMediator
import com.dshovhenia.compose.playgroundapp.data.remote.service.VimeoApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OptIn(androidx.paging.ExperimentalPagingApi::class)
class VideoRepository @Inject constructor(
    private val vimeoApiService: VimeoApiService,
    private val vimeoDatabase: VimeoDatabase,
    private val videoDbHelper: VideoDbHelper
) {

    fun getVideos(
        initialUri: String,
        searchQuery: String?
    ) = Pager(
        config = PagingConfig(pageSize = NETWORK_PAGE_SIZE),
        remoteMediator = VideoRemoteMediator(
            initialUri,
            searchQuery,
            videoDbHelper,
            vimeoApiService,
            NETWORK_PAGE_SIZE
        ),
        pagingSourceFactory = { vimeoDatabase.videoDao().getVideos() }
    ).liveData

    fun getVideosFlow(
        initialUri: String,
        keyword: String?
    ) = Pager(
        config = PagingConfig(pageSize = NETWORK_PAGE_SIZE),
        remoteMediator = VideoRemoteMediator(
            initialUri,
            keyword,
            videoDbHelper,
            vimeoApiService,
            NETWORK_PAGE_SIZE
        ),
        pagingSourceFactory = { vimeoDatabase.videoDao().getVideos() }
    ).flow

    fun getVideoById(videoId: Long) =
        vimeoDatabase.videoDao().getVideoById(videoId)

    suspend fun clearVideos() = videoDbHelper.clear()

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }
}
