package com.dshovhenia.compose.playgroundapp.domain.usecase

import com.dshovhenia.compose.playgroundapp.domain.repository.VideoRepository
import javax.inject.Inject

class GetVideoByIdUseCase @Inject constructor(private val videoRepository: VideoRepository) {

    fun launch(videoId: Long) = videoRepository.getVideoById(videoId)

}
