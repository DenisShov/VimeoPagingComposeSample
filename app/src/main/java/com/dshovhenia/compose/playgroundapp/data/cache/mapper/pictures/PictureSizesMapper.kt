package com.dshovhenia.compose.playgroundapp.data.cache.mapper.pictures

import com.dshovhenia.compose.playgroundapp.data.cache.mapper.Mapper
import com.dshovhenia.compose.playgroundapp.data.cache.model.pictures.CachedPictureSizes
import com.dshovhenia.compose.playgroundapp.data.model.pictures.PictureSizes
import javax.inject.Inject

class PictureSizesMapper @Inject constructor() : Mapper<CachedPictureSizes, PictureSizes> {

    override fun mapFrom(type: CachedPictureSizes) =
        PictureSizes(type.width, type.height, type.link)

    override fun mapTo(type: PictureSizes): CachedPictureSizes {
        val cachedPictureSizes = CachedPictureSizes()
        cachedPictureSizes.width = type.width
        cachedPictureSizes.height = type.height
        cachedPictureSizes.link = type.link
        return cachedPictureSizes
    }
}
