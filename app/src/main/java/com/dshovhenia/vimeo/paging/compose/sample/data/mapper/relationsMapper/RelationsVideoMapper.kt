package com.dshovhenia.vimeo.paging.compose.sample.data.mapper.relationsMapper

import com.dshovhenia.vimeo.paging.compose.sample.data.local.db.model.video.CachedVideo
import com.dshovhenia.vimeo.paging.compose.sample.data.local.db.model.video.RelationsVideo

fun RelationsVideo.toCachedVideo(): CachedVideo {
    relationsUser?.user?.pictureSizes = relationsUser?.pictureSizes ?: ArrayList()

    return CachedVideo(
        video.id,
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
