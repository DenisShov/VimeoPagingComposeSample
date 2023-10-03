package com.dshovhenia.compose.playgroundapp.data.cache.mapper.user

import com.dshovhenia.compose.playgroundapp.data.cache.mapper.pictures.toCachedPictureSizes
import com.dshovhenia.compose.playgroundapp.data.cache.mapper.pictures.toPictureSizes
import com.dshovhenia.compose.playgroundapp.data.cache.model.user.CachedUser
import com.dshovhenia.compose.playgroundapp.data.model.Connection
import com.dshovhenia.compose.playgroundapp.data.model.pictures.Pictures
import com.dshovhenia.compose.playgroundapp.data.model.user.User
import com.dshovhenia.compose.playgroundapp.data.model.user.UserMetadata

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
        name, pictures, userMetadata
    )
}

fun User.toCachedUser() = CachedUser(name,
    metadata?.followersConnection?.uri ?: "",
    metadata?.followersConnection?.total ?: 0,
    metadata?.videosConnection?.uri ?: "",
    metadata?.videosConnection?.total ?: 0,
    pictures?.sizes?.map { it.toCachedPictureSizes() } ?: ArrayList())
