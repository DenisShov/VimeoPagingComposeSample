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
    var id: Long = 0,
    var uri: String = "",
    var text: String = "",
    var created_on: Date? = null,
    var nextPage: String? = null,
    @Ignore
    var user: CachedUser? = null
)
