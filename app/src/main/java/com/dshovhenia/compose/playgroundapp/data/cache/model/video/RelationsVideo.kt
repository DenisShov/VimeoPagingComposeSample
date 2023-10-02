package com.dshovhenia.compose.playgroundapp.data.cache.model.video

import androidx.room.Embedded
import androidx.room.Relation
import com.dshovhenia.compose.playgroundapp.data.cache.model.pictures.CachedPictureSizes
import com.dshovhenia.compose.playgroundapp.data.cache.model.user.CachedUser
import com.dshovhenia.compose.playgroundapp.data.cache.model.user.RelationsUser
import com.dshovhenia.compose.playgroundapp.data.cache.model.video.CachedVideo

data class RelationsVideo(
  @Embedded
  val video: CachedVideo,
  @Relation(
    parentColumn = "id",
    entityColumn = "videoId",
    entity = CachedUser::class
  )
  var relationsUser: RelationsUser? = null,
  @Relation(
    parentColumn = "id",
    entityColumn = "videoId",
    entity = CachedPictureSizes::class
  )
  var relationsPictureSizes: List<CachedPictureSizes> = ArrayList()
)
