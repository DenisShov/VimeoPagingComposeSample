package com.dshovhenia.vimeo.paging.compose.sample.data.local.db.model.comment

import androidx.room.Embedded
import androidx.room.Relation
import com.dshovhenia.vimeo.paging.compose.sample.data.local.db.model.user.CachedUser
import com.dshovhenia.vimeo.paging.compose.sample.data.local.db.model.user.RelationsUser

data class RelationsComment(
    @Embedded
    val comment: CachedComment,
    @Relation(
        parentColumn = "id",
        entityColumn = "commentId",
        entity = CachedUser::class
    )
    val relationsUser: RelationsUser
)
