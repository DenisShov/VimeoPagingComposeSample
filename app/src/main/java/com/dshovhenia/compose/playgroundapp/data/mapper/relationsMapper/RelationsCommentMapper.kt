package com.dshovhenia.compose.playgroundapp.data.mapper.relationsMapper

import com.dshovhenia.compose.playgroundapp.data.local.db.model.comment.CachedComment
import com.dshovhenia.compose.playgroundapp.data.local.db.model.comment.RelationsComment

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
