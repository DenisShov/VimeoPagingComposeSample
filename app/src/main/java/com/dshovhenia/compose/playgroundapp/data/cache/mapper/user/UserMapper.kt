package com.dshovhenia.compose.playgroundapp.data.cache.mapper.user

import com.dshovhenia.compose.playgroundapp.data.cache.mapper.Mapper
import com.dshovhenia.compose.playgroundapp.data.cache.mapper.pictures.PictureSizesMapper
import com.dshovhenia.compose.playgroundapp.data.cache.model.user.CachedUser
import com.dshovhenia.compose.playgroundapp.data.model.Connection
import com.dshovhenia.compose.playgroundapp.data.model.pictures.Pictures
import com.dshovhenia.compose.playgroundapp.data.model.user.User
import com.dshovhenia.compose.playgroundapp.data.model.user.UserMetadata
import javax.inject.Inject

class UserMapper @Inject constructor(
  private val pictureSizesMapper: PictureSizesMapper
) : Mapper<CachedUser, User> {

  override fun mapFrom(type: CachedUser): User {
    val pictures = Pictures()
    pictures.sizes = type.pictureSizes.map { pictureSizesMapper.mapFrom(it) }

    val userMetadata = UserMetadata()
    val followersConnection = Connection()
    followersConnection.uri = type.followersUri
    followersConnection.total = type.followersTotal
    val videosConnection = Connection()
    videosConnection.uri = type.videosUri
    videosConnection.total = type.videosTotal
    userMetadata.followersConnection = followersConnection
    userMetadata.videosConnection = videosConnection

    return User(
      type.name,
      pictures,
      userMetadata
    )
  }

  override fun mapTo(type: User) =
    CachedUser(
      type.name,
      type.metadata?.followersConnection?.uri ?: "",
      type.metadata?.followersConnection?.total ?: 0,
      type.metadata?.videosConnection?.uri ?: "",
      type.metadata?.videosConnection?.total ?: 0,
      type.pictures?.sizes?.map { pictureSizes -> pictureSizesMapper.mapTo(pictureSizes) }
        ?: ArrayList()
    )
}
