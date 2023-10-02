package com.dshovhenia.compose.playgroundapp.data.cache.mapper.video

import com.dshovhenia.compose.playgroundapp.data.cache.mapper.Mapper
import com.dshovhenia.compose.playgroundapp.data.cache.mapper.pictures.PictureSizesMapper
import com.dshovhenia.compose.playgroundapp.data.cache.mapper.user.UserMapper
import com.dshovhenia.compose.playgroundapp.data.cache.model.video.CachedVideo
import com.dshovhenia.compose.playgroundapp.data.model.Connection
import com.dshovhenia.compose.playgroundapp.data.model.pictures.Pictures
import com.dshovhenia.compose.playgroundapp.data.model.video.Video
import com.dshovhenia.compose.playgroundapp.data.model.video.VideoMetadata
import com.dshovhenia.compose.playgroundapp.data.model.video.VideoStats
import javax.inject.Inject

class VideoMapper @Inject constructor(
    private val userMapper: UserMapper,
    private val pictureSizesMapper: PictureSizesMapper
) : Mapper<CachedVideo, Video> {

    override fun mapFrom(type: CachedVideo): Video {
        val pictures = Pictures()
        pictures.sizes = type.pictureSizes.map { pictureSizesMapper.mapFrom(it) }

        val videoMetadata = VideoMetadata()
        val commentsConnection = Connection()
        commentsConnection.uri = type.commentsUri
        commentsConnection.total = type.commentsTotal
        val likesConnection = Connection()
        likesConnection.uri = type.likesUri
        likesConnection.total = type.likesTotal
        videoMetadata.commentsConnection = commentsConnection
        videoMetadata.likesConnection = likesConnection

        val videoStats = VideoStats()
        videoStats.plays = type.videoPlays

        return Video(
            type.uri, type.name, type.description, type.duration, type.createdTime, type.nextPage,
            type.user?.let { userMapper.mapFrom(it) },
            pictures,
            videoMetadata,
            videoStats
        )
    }

    override fun mapTo(type: Video) =
        CachedVideo(type.uri,
            type.name,
            type.description ?: "",
            type.duration,
            type.createdTime,
            type.nextPage,
            type.metadata?.commentsConnection?.uri ?: "",
            type.metadata?.commentsConnection?.total ?: 0,
            type.metadata?.likesConnection?.uri ?: "",
            type.metadata?.likesConnection?.total ?: 0,
            type.stats?.plays ?: 0,
            type.user?.let { userMapper.mapTo(it) },
            type.pictures?.sizes?.map { pictureSizesMapper.mapTo(it) } ?: ArrayList()
        )
}
