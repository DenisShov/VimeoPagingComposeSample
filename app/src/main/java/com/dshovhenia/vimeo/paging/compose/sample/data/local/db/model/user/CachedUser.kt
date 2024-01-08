package com.dshovhenia.vimeo.paging.compose.sample.data.local.db.model.user

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey
import com.dshovhenia.vimeo.paging.compose.sample.data.local.db.DbConstants
import com.dshovhenia.vimeo.paging.compose.sample.data.local.db.model.comment.CachedComment
import com.dshovhenia.vimeo.paging.compose.sample.data.local.db.model.pictures.CachedPictureSizes
import com.dshovhenia.vimeo.paging.compose.sample.data.local.db.model.video.CachedVideo

@Entity(
    tableName = DbConstants.USER_TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = CachedComment::class,
            parentColumns = ["id"],
            childColumns = ["commentId"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = CachedVideo::class,
            parentColumns = ["id"],
            childColumns = ["videoId"],
            onDelete = CASCADE
        )],
    indices = [Index(value = ["commentId"]), Index(value = ["videoId"])]
)
data class CachedUser(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var commentId: Long? = null,
    var videoId: Long? = null,

    var name: String = "",
    var followersUri: String = "",
    var followersTotal: Int = 0,
    var videosUri: String = "",
    var videosTotal: Int = 0,
    @Ignore
    var pictureSizes: List<CachedPictureSizes> = ArrayList()
)
