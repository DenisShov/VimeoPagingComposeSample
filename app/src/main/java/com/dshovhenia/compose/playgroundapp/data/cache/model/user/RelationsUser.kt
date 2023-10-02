package com.dshovhenia.compose.playgroundapp.data.cache.model.user

import androidx.room.Embedded
import androidx.room.Relation
import com.dshovhenia.compose.playgroundapp.data.cache.model.pictures.CachedPictureSizes
import com.dshovhenia.compose.playgroundapp.data.cache.model.user.CachedUser

data class RelationsUser(
  @Embedded
  val user: CachedUser,
  @Relation(
    parentColumn = "id",
    entityColumn = "userId",
    entity = CachedPictureSizes::class
  )
  val pictureSizes: List<CachedPictureSizes>
)
