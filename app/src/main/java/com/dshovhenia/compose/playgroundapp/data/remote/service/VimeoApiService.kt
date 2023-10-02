package com.dshovhenia.compose.playgroundapp.data.remote.service

import com.dshovhenia.compose.playgroundapp.data.cache.preferences.model.AccessToken
import com.dshovhenia.compose.playgroundapp.data.model.Collection
import com.dshovhenia.compose.playgroundapp.data.model.comment.Comment
import com.dshovhenia.compose.playgroundapp.data.model.video.Video
import retrofit2.Response
import retrofit2.http.*

interface VimeoApiService {

  @POST("oauth/authorize/client?grant_type=client_credentials")
  suspend fun getUnauthenticatedToken(@Header("Authorization") basicAuth: String?): AccessToken

  @GET
  suspend fun getVideos(
    @Url url: String?,
    @Query("query") query: String?,
    @Query("page") page: Int?,
    @Query("per_page") perPage: Int?
  ): Response<Collection<Video>>

  @GET
  suspend fun getComments(
    @Url url: String?, @Query("direction") direction: String?,
    @Query("page") page: Int?, @Query("per_page") perPage: Int?
  ): Response<Collection<Comment>>
}
