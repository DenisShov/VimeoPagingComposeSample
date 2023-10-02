package com.dshovhenia.compose.playgroundapp.data.cache.model.video

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.dshovhenia.compose.playgroundapp.data.cache.db.DbConstants
import com.dshovhenia.compose.playgroundapp.data.cache.model.pictures.CachedPictureSizes
import com.dshovhenia.compose.playgroundapp.data.cache.model.user.CachedUser
import java.util.*
import kotlin.collections.ArrayList

@Entity(tableName = DbConstants.VIDEO_TABLE_NAME)
data class CachedVideo(
  var uri: String = "",
  var name: String = "",
  var description: String = "",
  var duration: Int = 0,
  var createdTime: Date? = null,
  var nextPage: String = "",

  var commentsUri: String = "",
  var commentsTotal: Int = 0,
  var likesUri: String = "",
  var likesTotal: Int = 0,
  var videoPlays: Long = 0,
  @Ignore
  var user: CachedUser? = null,
  @Ignore
  var pictureSizes: List<CachedPictureSizes> = ArrayList()
) {
  @PrimaryKey(autoGenerate = true)
  var id: Long? = null
}
