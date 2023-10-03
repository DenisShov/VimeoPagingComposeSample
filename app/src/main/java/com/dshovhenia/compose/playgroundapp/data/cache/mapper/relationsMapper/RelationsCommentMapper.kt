package com.dshovhenia.compose.playgroundapp.data.cache.mapper.relationsMapper

import com.dshovhenia.compose.playgroundapp.data.cache.model.comment.CachedComment
import com.dshovhenia.compose.playgroundapp.data.cache.model.comment.RelationsComment

fun RelationsComment.toCachedComment(): CachedComment {
    relationsUser.user.pictureSizes = relationsUser.pictureSizes

    return CachedComment(
        uri = comment.uri,
        text = comment.text,
        created_on = comment.created_on,
        nextPage = comment.nextPage,
        user = relationsUser.user
    )
}
