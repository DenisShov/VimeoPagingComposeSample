package com.dshovhenia.vimeo.paging.compose.sample.data.mapper.video

import com.dshovhenia.vimeo.paging.compose.sample.data.mapper.pictures.toCachedPictureSizes
import com.dshovhenia.vimeo.paging.compose.sample.data.mapper.pictures.toPictureSizes
import com.dshovhenia.vimeo.paging.compose.sample.data.mapper.user.toCachedUser
import com.dshovhenia.vimeo.paging.compose.sample.data.mapper.user.toUser
import com.dshovhenia.vimeo.paging.compose.sample.data.local.db.model.video.CachedVideo
import com.dshovhenia.vimeo.paging.compose.sample.data.remote.model.Connection
import com.dshovhenia.vimeo.paging.compose.sample.data.remote.model.pictures.Pictures
import com.dshovhenia.vimeo.paging.compose.sample.data.remote.model.video.Video
import com.dshovhenia.vimeo.paging.compose.sample.data.remote.model.video.VideoMetadata
import com.dshovhenia.vimeo.paging.compose.sample.data.remote.model.video.VideoStats

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
