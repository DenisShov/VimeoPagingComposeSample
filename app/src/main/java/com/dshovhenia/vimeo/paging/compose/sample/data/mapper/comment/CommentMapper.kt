package com.dshovhenia.vimeo.paging.compose.sample.data.mapper.comment

import com.dshovhenia.vimeo.paging.compose.sample.data.mapper.user.toCachedUser
import com.dshovhenia.vimeo.paging.compose.sample.data.mapper.user.toUser
import com.dshovhenia.vimeo.paging.compose.sample.data.local.db.model.comment.CachedComment
import com.dshovhenia.vimeo.paging.compose.sample.data.remote.model.comment.Comment

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
