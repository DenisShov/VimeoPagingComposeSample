package com.dshovhenia.compose.playgroundapp.data.local.db.model.video

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.dshovhenia.compose.playgroundapp.data.local.db.DbConstants
import com.dshovhenia.compose.playgroundapp.data.local.db.model.pictures.CachedPictureSizes
import com.dshovhenia.compose.playgroundapp.data.local.db.model.user.CachedUser
import java.util.*
import kotlin.collections.ArrayList

@Entity(tableName = DbConstants.VIDEO_TABLE_NAME)
data class CachedVideo(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var uri: String = "",
    var name: String = "",
    var description: String = "",
    var duration: Int = 0,
    var createdTime: Date? = null,
    var nextPage: String? = null,

    var commentsUri: String = "",
    var commentsTotal: Int = 0,
    var likesUri: String = "",
    var likesTotal: Int = 0,
    var videoPlays: Long = 0,
    @Ignore
    var user: CachedUser? = null,
    @Ignore
    var pictureSizes: List<CachedPictureSizes> = ArrayList()
)