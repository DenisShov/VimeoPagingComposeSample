package com.dshovhenia.vimeo.paging.compose.sample.data.mapper.pictures

import com.dshovhenia.vimeo.paging.compose.sample.data.local.db.model.pictures.CachedPictureSizes
import com.dshovhenia.vimeo.paging.compose.sample.data.remote.model.pictures.PictureSizes

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
