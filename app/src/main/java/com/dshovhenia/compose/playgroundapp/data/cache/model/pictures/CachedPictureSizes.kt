package com.dshovhenia.compose.playgroundapp.data.cache.model.pictures

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey
import com.dshovhenia.compose.playgroundapp.data.cache.db.DbConstants
import com.dshovhenia.compose.playgroundapp.data.cache.model.user.CachedUser
import com.dshovhenia.compose.playgroundapp.data.cache.model.video.CachedVideo

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
class CachedPictureSizes {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
    var videoId: Long? = null
    var userId: Long? = null

    var width: Int = 0
    var height: Int = 0
    var link: String = ""
}
