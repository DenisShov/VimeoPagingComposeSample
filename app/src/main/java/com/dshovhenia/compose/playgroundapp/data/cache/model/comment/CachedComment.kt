package com.dshovhenia.compose.playgroundapp.data.cache.model.comment

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.dshovhenia.compose.playgroundapp.data.cache.db.DbConstants
import com.dshovhenia.compose.playgroundapp.data.cache.model.user.CachedUser
import java.util.Date

@Entity(tableName = DbConstants.COMMENT_TABLE_NAME)
data class CachedComment(
    var uri: String = "",
    var text: String = "",
    var created_on: Date? = null,
    var nextPage: String = "",
    @Ignore
    var user: CachedUser? = null
) {

    @PrimaryKey(autoGenerate = true)
    var id: Long? = null

    fun initialize(relationsComment: RelationsComment): CachedComment {
        uri = relationsComment.comment.uri
        text = relationsComment.comment.text
        created_on = relationsComment.comment.created_on
        nextPage = relationsComment.comment.nextPage

        user = relationsComment.relationsUser.user
        user?.pictureSizes = relationsComment.relationsUser.pictureSizes

        return this
    }
}
