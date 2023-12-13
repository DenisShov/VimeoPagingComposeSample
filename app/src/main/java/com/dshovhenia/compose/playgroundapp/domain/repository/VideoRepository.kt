package com.dshovhenia.compose.playgroundapp.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.dshovhenia.compose.playgroundapp.data.local.db.VimeoDatabase
import com.dshovhenia.compose.playgroundapp.data.local.db.helper.VideoDbHelper
import com.dshovhenia.compose.playgroundapp.data.paging.videos.VideoRemoteMediator
import com.dshovhenia.compose.playgroundapp.data.remote.service.VimeoApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OptIn(androidx.paging.ExperimentalPagingApi::class)
class VideoRepository @Inject constructor(
    private val vimeoApiService: VimeoApiService,
    private val vimeoDatabase: VimeoDatabase,
    private val videoDbHelper: VideoDbHelper
) {

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

    suspend fun getVideoById(videoId: Long) =
        vimeoDatabase.videoDao().getVideoById(videoId)

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }
}
