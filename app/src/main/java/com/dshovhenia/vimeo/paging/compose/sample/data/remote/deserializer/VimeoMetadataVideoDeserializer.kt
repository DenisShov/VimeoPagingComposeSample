package com.dshovhenia.vimeo.paging.compose.sample.data.remote.deserializer

import com.dshovhenia.vimeo.paging.compose.sample.data.remote.model.Connection
import com.dshovhenia.vimeo.paging.compose.sample.data.remote.model.video.VideoMetadata
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type

class VimeoMetadataVideoDeserializer : JsonDeserializer<VideoMetadata> {

  @Throws(JsonParseException::class)
  override fun deserialize(
    json: JsonElement, typeOfT: Type, context: JsonDeserializationContext
  ): VideoMetadata {
    val jsonObject = json.asJsonObject

    val jsonConnectionsObject = jsonObject.getAsJsonObject(KEY_CONNECTIONS)
    val comments = context.deserialize<Connection>(
      jsonConnectionsObject.get(KEY_COMMENTS), Connection::class.java
    )
    val likes = context.deserialize<Connection>(
      jsonConnectionsObject.get(KEY_LIKES), Connection::class.java
    )

    return VideoMetadata(comments, likes)
  }

  companion object {
    private const val KEY_CONNECTIONS = "connections"
    private const val KEY_COMMENTS = "comments"
    private const val KEY_LIKES = "likes"
  }
}
