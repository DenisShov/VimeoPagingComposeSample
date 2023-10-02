package com.dshovhenia.compose.playgroundapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.dshovhenia.compose.playgroundapp.data.cache.db.VimeoDatabase
import com.dshovhenia.compose.playgroundapp.data.cache.helper.VideoDbHelper
import com.dshovhenia.compose.playgroundapp.data.cache.mapper.relationsMapper.RelationsVideoMapper
import com.dshovhenia.compose.playgroundapp.data.cache.mapper.video.VideoMapper
import com.dshovhenia.compose.playgroundapp.data.cache.model.video.CachedVideo
import com.dshovhenia.compose.playgroundapp.data.remote.service.VimeoApiService
import com.dshovhenia.compose.playgroundapp.paging.videos.VideoRemoteMediator
import com.dshovhenia.compose.playgroundapp.ui.home.HomeViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OptIn(androidx.paging.ExperimentalPagingApi::class)
class VideoRepository @Inject constructor(
    private val vimeoApiService: VimeoApiService,
    private val vimeoDatabase: VimeoDatabase,
    private val videoDbHelper: VideoDbHelper,
    private val videoMapper: VideoMapper,
    private val relationsVideoMapper: RelationsVideoMapper
) {

  fun getVideos(
    initialUri: String,
    searchQuery: String?
  ) = Pager(
    config = PagingConfig(pageSize = HomeViewModel.NETWORK_PAGE_SIZE),
    remoteMediator = VideoRemoteMediator(
      initialUri,
      searchQuery,
      videoDbHelper,
      videoMapper,
      vimeoApiService
    ),
    pagingSourceFactory = { vimeoDatabase.videoDao().getVideos() }
  ).liveData

  fun getVideoById(videoId: Long): CachedVideo {
    val relationsVideo = vimeoDatabase.videoDao().getVideoById(videoId)
    return relationsVideoMapper.mapFrom(relationsVideo)
  }

  fun clearVideos() = videoDbHelper.clear()
}
