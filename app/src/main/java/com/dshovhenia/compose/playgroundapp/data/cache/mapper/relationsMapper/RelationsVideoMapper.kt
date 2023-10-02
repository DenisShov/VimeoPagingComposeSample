package com.dshovhenia.compose.playgroundapp.data.cache.mapper.relationsMapper

import com.dshovhenia.compose.playgroundapp.data.cache.mapper.Mapper
import com.dshovhenia.compose.playgroundapp.data.cache.model.video.CachedVideo
import com.dshovhenia.compose.playgroundapp.data.cache.model.video.RelationsVideo
import javax.inject.Inject

class RelationsVideoMapper @Inject constructor() : Mapper<RelationsVideo, CachedVideo> {

  override fun mapFrom(type: RelationsVideo): CachedVideo {
    type.relationsUser?.user?.pictureSizes =
      type.relationsUser?.pictureSizes ?: ArrayList()

    return CachedVideo(
      type.video.uri,
      type.video.name,
      type.video.description,
      type.video.duration,
      type.video.createdTime,
      type.video.nextPage,

      type.video.commentsUri,
      type.video.commentsTotal,
      type.video.likesUri,
      type.video.likesTotal,
      type.video.videoPlays,
      type.relationsUser?.user,
      type.relationsPictureSizes
    )
  }

  override fun mapTo(type: CachedVideo): RelationsVideo {
    throw UnsupportedOperationException("Not supported transformation from CachedVideo to RelationsVideo.")
  }
}
