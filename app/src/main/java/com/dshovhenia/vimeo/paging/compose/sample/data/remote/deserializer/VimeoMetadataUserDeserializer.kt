package com.dshovhenia.vimeo.paging.compose.sample.data.remote.deserializer

import com.dshovhenia.vimeo.paging.compose.sample.data.remote.model.Connection
import com.dshovhenia.vimeo.paging.compose.sample.data.remote.model.user.UserMetadata
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type

class VimeoMetadataUserDeserializer : JsonDeserializer<UserMetadata> {

  @Throws(JsonParseException::class)
  override fun deserialize(
    json: JsonElement, typeOfT: Type, context: JsonDeserializationContext
  ): UserMetadata {
    val jsonObject = json.asJsonObject

    val jsonConnectionsObject = jsonObject.getAsJsonObject(KEY_CONNECTIONS)
    val followers = context.deserialize<Connection>(
      jsonConnectionsObject.get(KEY_FOLLOWERS), Connection::class.java
    )
    val videos = context.deserialize<Connection>(
      jsonConnectionsObject.get(KEY_VIDEOS), Connection::class.java
    )

    return UserMetadata(
      followers,
      videos
    )
  }

  companion object {
    private const val KEY_CONNECTIONS = "connections"
    private const val KEY_FOLLOWERS = "followers"
    private const val KEY_VIDEOS = "videos"
  }
}
