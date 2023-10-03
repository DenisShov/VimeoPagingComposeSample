package com.dshovhenia.compose.playgroundapp.data.cache.mapper.relationsMapper

import com.dshovhenia.compose.playgroundapp.data.cache.model.video.CachedVideo
import com.dshovhenia.compose.playgroundapp.data.cache.model.video.RelationsVideo

fun RelationsVideo.toCachedVideo(): CachedVideo {
    relationsUser?.user?.pictureSizes = relationsUser?.pictureSizes ?: ArrayList()

    return CachedVideo(
        video.uri,
        video.name,
        video.description,
        video.duration,
        video.createdTime,
        video.nextPage,

        video.commentsUri,
        video.commentsTotal,
        video.likesUri,
        video.likesTotal,
        video.videoPlays,
        relationsUser?.user,
        relationsPictureSizes
    )
}
