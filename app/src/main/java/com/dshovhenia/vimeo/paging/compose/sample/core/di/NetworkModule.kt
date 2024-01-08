package com.dshovhenia.vimeo.paging.compose.sample.core.di

import com.dshovhenia.vimeo.paging.compose.sample.data.remote.authentication.AuthenticationInterceptor
import com.dshovhenia.vimeo.paging.compose.sample.data.remote.authentication.VimeoServiceAuthenticator
import com.dshovhenia.vimeo.paging.compose.sample.data.remote.deserializer.VimeoMetadataUserDeserializer
import com.dshovhenia.vimeo.paging.compose.sample.data.remote.deserializer.VimeoMetadataVideoDeserializer
import com.dshovhenia.vimeo.paging.compose.sample.data.remote.model.user.UserMetadata
import com.dshovhenia.vimeo.paging.compose.sample.data.remote.model.video.VideoMetadata
import com.dshovhenia.vimeo.paging.compose.sample.data.remote.service.VimeoApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun loggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor { message ->
            Timber.i(message)
        }
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    fun okHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authenticationInterceptor: AuthenticationInterceptor,
        vimeoServiceAuthenticator: VimeoServiceAuthenticator
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authenticationInterceptor)
            .authenticator(vimeoServiceAuthenticator).build()
    }

    @Provides
    fun gson(): Gson {
        return GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .registerTypeHierarchyAdapter(
                UserMetadata::class.java, VimeoMetadataUserDeserializer()
            ).registerTypeHierarchyAdapter(
                VideoMetadata::class.java, VimeoMetadataVideoDeserializer()
            ).create()
    }

    @Provides
    fun retrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder().baseUrl("https://api.vimeo.com/")
            .addConverterFactory(GsonConverterFactory.create(gson)).client(okHttpClient).build()
    }

    @Provides
    fun vimeoApiService(vimeoRetrofit: Retrofit) =
        vimeoRetrofit.create(VimeoApiService::class.java)

    @Provides
    fun provideIODispatcher() = Dispatchers.IO

}
