package com.dshovhenia.compose.playgroundapp.data.mapper.comment

import com.dshovhenia.compose.playgroundapp.data.mapper.user.toCachedUser
import com.dshovhenia.compose.playgroundapp.data.mapper.user.toUser
import com.dshovhenia.compose.playgroundapp.data.local.db.model.comment.CachedComment
import com.dshovhenia.compose.playgroundapp.data.remote.model.comment.Comment

fun CachedComment.toComment() = Comment(
    uri = uri,
    text = text,
    createdOn = created_on,
    nextPage = nextPage,
    user = user?.toUser()
)

fun Comment.toCachedComment() = CachedComment(
    uri = uri,
    text = text,
    created_on = createdOn,
    nextPage = nextPage,
    user = user?.toCachedUser()
)
