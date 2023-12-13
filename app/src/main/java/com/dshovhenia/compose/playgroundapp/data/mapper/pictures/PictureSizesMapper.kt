package com.dshovhenia.compose.playgroundapp.data.mapper.pictures

import com.dshovhenia.compose.playgroundapp.data.local.db.model.pictures.CachedPictureSizes
import com.dshovhenia.compose.playgroundapp.data.remote.model.pictures.PictureSizes

fun CachedPictureSizes.toPictureSizes() = PictureSizes(
    width = width,
    height = height,
    link = link
)

fun PictureSizes.toCachedPictureSizes() = CachedPictureSizes(
    videoId = null,
    userId = null,
    width = width,
    height = height,
    link = link
)
