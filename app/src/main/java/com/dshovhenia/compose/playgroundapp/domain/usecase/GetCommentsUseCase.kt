package com.dshovhenia.compose.playgroundapp.domain.usecase

import com.dshovhenia.compose.playgroundapp.domain.repository.CommentRepository
import javax.inject.Inject

class GetCommentsUseCase @Inject constructor(private val commentRepository: CommentRepository) {

    fun launch(commentUrl: String) = commentRepository.getCommentsFlow(commentUrl)

}
