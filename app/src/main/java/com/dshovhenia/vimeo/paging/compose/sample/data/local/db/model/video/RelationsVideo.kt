package com.dshovhenia.vimeo.paging.compose.sample.data.local.db.model.video

import androidx.room.Embedded
import androidx.room.Relation
import com.dshovhenia.vimeo.paging.compose.sample.data.local.db.model.pictures.CachedPictureSizes
import com.dshovhenia.vimeo.paging.compose.sample.data.local.db.model.user.CachedUser
import com.dshovhenia.vimeo.paging.compose.sample.data.local.db.model.user.RelationsUser

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
