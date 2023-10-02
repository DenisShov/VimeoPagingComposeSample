package com.dshovhenia.compose.playgroundapp.data.cache.model.user

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.dshovhenia.compose.playgroundapp.data.cache.db.DbConstants
import com.dshovhenia.compose.playgroundapp.data.cache.model.comment.CachedComment
import com.dshovhenia.compose.playgroundapp.data.cache.model.pictures.CachedPictureSizes
import com.dshovhenia.compose.playgroundapp.data.cache.model.video.CachedVideo
import java.util.*

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
  var name: String = "",
  var followersUri: String = "",
  var followersTotal: Int = 0,
  var videosUri: String = "",
  var videosTotal: Int = 0,
  @Ignore
  var pictureSizes: List<CachedPictureSizes> = ArrayList()
) {
  @PrimaryKey(autoGenerate = true)
  var id: Long? = null
  var commentId: Long? = null
  var videoId: Long? = null
}
