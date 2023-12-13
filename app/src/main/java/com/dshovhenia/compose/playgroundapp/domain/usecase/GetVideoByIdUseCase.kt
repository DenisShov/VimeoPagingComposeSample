package com.dshovhenia.compose.playgroundapp.domain.usecase

import com.dshovhenia.compose.playgroundapp.data.remote.functional.wrapResult
import com.dshovhenia.compose.playgroundapp.domain.repository.VideoRepository
import javax.inject.Inject

class GetVideoByIdUseCase @Inject constructor(private val videoRepository: VideoRepository) {

    suspend fun launch(videoId: Long) = wrapResult { videoRepository.getVideoById(videoId) }

}
