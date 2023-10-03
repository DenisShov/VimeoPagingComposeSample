package com.dshovhenia.compose.playgroundapp.data.cache.mapper.pictures

import com.dshovhenia.compose.playgroundapp.data.cache.model.pictures.CachedPictureSizes
import com.dshovhenia.compose.playgroundapp.data.model.pictures.PictureSizes

fun CachedPictureSizes.toPictureSizes() = PictureSizes(width, height, link)

fun PictureSizes.toCachedPictureSizes(): CachedPictureSizes {
    val cachedPictureSizes = CachedPictureSizes()
    cachedPictureSizes.width = width
    cachedPictureSizes.height = height
    cachedPictureSizes.link = link
    return cachedPictureSizes
}