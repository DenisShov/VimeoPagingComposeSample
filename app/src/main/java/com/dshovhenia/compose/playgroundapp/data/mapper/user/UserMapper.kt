package com.dshovhenia.compose.playgroundapp.data.mapper.user

import com.dshovhenia.compose.playgroundapp.data.mapper.pictures.toCachedPictureSizes
import com.dshovhenia.compose.playgroundapp.data.mapper.pictures.toPictureSizes
import com.dshovhenia.compose.playgroundapp.data.local.db.model.user.CachedUser
import com.dshovhenia.compose.playgroundapp.data.remote.model.Connection
import com.dshovhenia.compose.playgroundapp.data.remote.model.pictures.Pictures
import com.dshovhenia.compose.playgroundapp.data.remote.model.user.User
import com.dshovhenia.compose.playgroundapp.data.remote.model.user.UserMetadata

fun CachedUser.toUser(): User {
    val pictures = Pictures()
    pictures.sizes = pictureSizes.map { it.toPictureSizes() }

    val userMetadata = UserMetadata()
    val followersConnection = Connection()
    followersConnection.uri = followersUri
    followersConnection.total = followersTotal
    val videosConnection = Connection()
    videosConnection.uri = videosUri
    videosConnection.total = videosTotal
    userMetadata.followersConnection = followersConnection
    userMetadata.videosConnection = videosConnection

    return User(
        name = name,
        pictures = pictures,
        metadata = userMetadata
    )
}

fun User.toCachedUser() = CachedUser(
    commentId = null,
    videoId = null,
    name = name,
    followersUri = metadata?.followersConnection?.uri ?: "",
    followersTotal = metadata?.followersConnection?.total ?: 0,
    videosUri = metadata?.videosConnection?.uri ?: "",
    videosTotal = metadata?.videosConnection?.total ?: 0,
    pictureSizes = pictures?.sizes?.map { it.toCachedPictureSizes() } ?: ArrayList()
)
