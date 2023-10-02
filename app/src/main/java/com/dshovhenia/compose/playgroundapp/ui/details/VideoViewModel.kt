package com.dshovhenia.compose.playgroundapp.ui.details

import androidx.lifecycle.ViewModel
import com.dshovhenia.compose.playgroundapp.data.cache.model.video.CachedVideo
import com.dshovhenia.compose.playgroundapp.data.repository.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    private var videoRepository: VideoRepository
) : ViewModel() {

    fun getVideoById(videoId: Long): CachedVideo {
        return videoRepository.getVideoById(videoId)
    }
}
