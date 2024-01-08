package com.dshovhenia.vimeo.paging.compose.sample.data.local.db.model.pictures

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.dshovhenia.vimeo.paging.compose.sample.data.local.db.DbConstants
import com.dshovhenia.vimeo.paging.compose.sample.data.local.db.model.user.CachedUser
import com.dshovhenia.vimeo.paging.compose.sample.data.local.db.model.video.CachedVideo

@Entity(
    tableName = DbConstants.PICTURE_SIZES_TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = CachedVideo::class,
            parentColumns = ["id"],
            childColumns = ["videoId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CachedUser::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )],
    indices = [Index(value = ["videoId"]), Index(value = ["userId"])]
)
class CachedPictureSizes(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var videoId: Long? = null,
    var userId: Long? = null,

    var width: Int = 0,
    var height: Int = 0,
    var link: String = ""
)
