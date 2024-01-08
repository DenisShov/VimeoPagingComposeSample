package com.dshovhenia.vimeo.paging.compose.sample.domain.usecase

import com.dshovhenia.vimeo.paging.compose.sample.domain.repository.CommentRepository
import javax.inject.Inject

class GetCommentsUseCase @Inject constructor(private val commentRepository: CommentRepository) {

    fun launch(commentUrl: String) = commentRepository.getCommentsFlow(commentUrl)

}
