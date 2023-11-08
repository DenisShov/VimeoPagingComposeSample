package com.dshovhenia.compose.playgroundapp.data.local.db.helper

import androidx.room.withTransaction
import com.dshovhenia.compose.playgroundapp.data.local.db.VimeoDatabase
import com.dshovhenia.compose.playgroundapp.data.local.db.model.user.CachedUser
import com.dshovhenia.compose.playgroundapp.data.local.db.model.video.CachedVideo
import timber.log.Timber
import javax.inject.Inject

class VideoDbHelper @Inject constructor(val vimeoDatabase: VimeoDatabase) {

    val videoDao = vimeoDatabase.videoDao()
    var userDao = vimeoDatabase.userDao()
    var pictureSizesDao = vimeoDatabase.pictureSizesDao()

    suspend fun clear() = videoDao.clearVideos()

    suspend fun insertVideos(videos: List<CachedVideo>) {
        Timber.d("saving videos")

        vimeoDatabase.withTransaction {
            for (video in videos) {
                insertVideo(video)
            }
        }
    }

    private suspend fun insertVideo(video: CachedVideo) {
        Timber.d("saving video")
        val id = videoDao.insertVideo(video)

        video.user?.also {
            it.videoId = id
            insertUser(it)
        }

        video.pictureSizes.forEach {
            it.videoId = id
        }
        video.pictureSizes.also {
            pictureSizesDao.insertPictureSizes(it)
        }
    }

    private suspend fun insertUser(user: CachedUser) {
        Timber.d("saving user")
        val id = userDao.insertUser(user)

        user.pictureSizes.forEach {
            it.userId = id
        }
        user.pictureSizes.also {
            pictureSizesDao.insertPictureSizes(it)
        }
    }
}
