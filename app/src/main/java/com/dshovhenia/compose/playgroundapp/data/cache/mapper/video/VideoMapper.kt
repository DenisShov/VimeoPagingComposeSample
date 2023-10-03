package com.dshovhenia.compose.playgroundapp.data.cache.mapper.video

import com.dshovhenia.compose.playgroundapp.data.cache.mapper.pictures.toCachedPictureSizes
import com.dshovhenia.compose.playgroundapp.data.cache.mapper.pictures.toPictureSizes
import com.dshovhenia.compose.playgroundapp.data.cache.mapper.user.toCachedUser
import com.dshovhenia.compose.playgroundapp.data.cache.mapper.user.toUser
import com.dshovhenia.compose.playgroundapp.data.cache.model.video.CachedVideo
import com.dshovhenia.compose.playgroundapp.data.model.Connection
import com.dshovhenia.compose.playgroundapp.data.model.pictures.Pictures
import com.dshovhenia.compose.playgroundapp.data.model.video.Video
import com.dshovhenia.compose.playgroundapp.data.model.video.VideoMetadata
import com.dshovhenia.compose.playgroundapp.data.model.video.VideoStats

fun CachedVideo.toVideo(): Video {
    val pictures = Pictures()
    pictures.sizes = pictureSizes.map { it.toPictureSizes() }

    val videoMetadata = VideoMetadata()
    val commentsConnection = Connection()
    commentsConnection.uri = commentsUri
    commentsConnection.total = commentsTotal
    val likesConnection = Connection()
    likesConnection.uri = likesUri
    likesConnection.total = likesTotal
    videoMetadata.commentsConnection = commentsConnection
    videoMetadata.likesConnection = likesConnection

    val videoStats = VideoStats()
    videoStats.plays = videoPlays

    return Video(
        uri,
        name,
        description,
        duration,
        createdTime,
        nextPage,
        user?.let { it.toUser() },
        pictures,
        videoMetadata,
        videoStats
    )
}

fun Video.toCachedVideo() = CachedVideo(uri,
    name,
    description ?: "",
    duration,
    createdTime,
    nextPage,
    metadata?.commentsConnection?.uri ?: "",
    metadata?.commentsConnection?.total ?: 0,
    metadata?.likesConnection?.uri ?: "",
    metadata?.likesConnection?.total ?: 0,
    stats?.plays ?: 0,
    user?.let { it.toCachedUser() },
    pictures?.sizes?.map { it.toCachedPictureSizes() } ?: ArrayList())
