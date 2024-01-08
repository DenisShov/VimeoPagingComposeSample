package com.dshovhenia.vimeo.paging.compose.sample.domain.usecase

import com.dshovhenia.vimeo.paging.compose.sample.data.remote.functional.wrapResult
import com.dshovhenia.vimeo.paging.compose.sample.domain.repository.VideoRepository
import javax.inject.Inject

class GetVideoByIdUseCase @Inject constructor(private val videoRepository: VideoRepository) {

    suspend fun launch(videoId: Long) = wrapResult { videoRepository.getVideoById(videoId) }

}
