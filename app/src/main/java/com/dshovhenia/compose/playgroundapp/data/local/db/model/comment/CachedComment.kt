package com.dshovhenia.compose.playgroundapp.data.local.db.model.comment

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.dshovhenia.compose.playgroundapp.data.local.db.DbConstants
import com.dshovhenia.compose.playgroundapp.data.local.db.model.user.CachedUser
import java.util.Date

@Entity(tableName = DbConstants.COMMENT_TABLE_NAME)
data class CachedComment(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var uri: String = "",
    var text: String = "",
    var created_on: Date? = null,
    var nextPage: String = "",
    @Ignore
    var user: CachedUser? = null
)
