package com.dshovhenia.compose.playgroundapp.domain.usecase

import com.dshovhenia.compose.playgroundapp.domain.repository.VideoRepository
import javax.inject.Inject

class GetVideosUseCase @Inject constructor(private val videoRepository: VideoRepository) {

    fun launch(
        initialUri: String,
        keyword: String?
    ) = videoRepository.getVideosFlow(initialUri, keyword)

}
