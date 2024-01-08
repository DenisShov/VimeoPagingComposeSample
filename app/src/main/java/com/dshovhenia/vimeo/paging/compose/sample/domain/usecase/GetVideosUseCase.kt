package com.dshovhenia.vimeo.paging.compose.sample.domain.usecase

import com.dshovhenia.vimeo.paging.compose.sample.domain.repository.VideoRepository
import javax.inject.Inject

class GetVideosUseCase @Inject constructor(private val videoRepository: VideoRepository) {

    fun launch(
        initialUri: String,
        keyword: String?
    ) = videoRepository.getVideosFlow(initialUri, keyword)

}
