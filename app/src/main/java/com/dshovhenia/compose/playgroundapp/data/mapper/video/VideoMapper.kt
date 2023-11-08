package com.dshovhenia.compose.playgroundapp.data.mapper.video

import com.dshovhenia.compose.playgroundapp.data.mapper.pictures.toCachedPictureSizes
import com.dshovhenia.compose.playgroundapp.data.mapper.pictures.toPictureSizes
import com.dshovhenia.compose.playgroundapp.data.mapper.user.toCachedUser
import com.dshovhenia.compose.playgroundapp.data.mapper.user.toUser
import com.dshovhenia.compose.playgroundapp.data.local.db.model.video.CachedVideo
import com.dshovhenia.compose.playgroundapp.data.remote.model.Connection
import com.dshovhenia.compose.playgroundapp.data.remote.model.pictures.Pictures
import com.dshovhenia.compose.playgroundapp.data.remote.model.video.Video
import com.dshovhenia.compose.playgroundapp.data.remote.model.video.VideoMetadata
import com.dshovhenia.compose.playgroundapp.data.remote.model.video.VideoStats

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
        uri = uri,
        name = name,
        description = description,
        duration = duration,
        createdTime = createdTime,
        nextPage = nextPage,
        user = user?.toUser(),
        pictures = pictures,
        metadata = videoMetadata,
        stats = videoStats
    )
}

fun Video.toCachedVideo() = CachedVideo(
    id = null,
    uri = uri,
    name = name,
    description = description ?: "",
    duration = duration,
    createdTime = createdTime,
    nextPage = nextPage,
    commentsUri = metadata?.commentsConnection?.uri ?: "",
    commentsTotal = metadata?.commentsConnection?.total ?: 0,
    likesUri = metadata?.likesConnection?.uri ?: "",
    likesTotal = metadata?.likesConnection?.total ?: 0,
    videoPlays = stats?.plays ?: 0,
    user = user?.toCachedUser(),
    pictureSizes = pictures?.sizes?.map { it.toCachedPictureSizes() } ?: ArrayList()
)
