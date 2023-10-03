package com.dshovhenia.compose.playgroundapp.data.cache.mapper.comment

import com.dshovhenia.compose.playgroundapp.data.cache.mapper.user.toCachedUser
import com.dshovhenia.compose.playgroundapp.data.cache.mapper.user.toUser
import com.dshovhenia.compose.playgroundapp.data.cache.model.comment.CachedComment
import com.dshovhenia.compose.playgroundapp.data.model.comment.Comment

fun CachedComment.toComment() = Comment(uri, text, created_on, nextPage, user?.let { it.toUser() })

fun Comment.toCachedComment() =
    CachedComment(uri, text, createdOn, nextPage, user?.let { it.toCachedUser() })
