package com.dshovhenia.vimeo.paging.compose.sample.data.local.db.helper

import androidx.room.withTransaction
import com.dshovhenia.vimeo.paging.compose.sample.data.local.db.VimeoDatabase
import com.dshovhenia.vimeo.paging.compose.sample.data.local.db.model.comment.CachedComment
import com.dshovhenia.vimeo.paging.compose.sample.data.local.db.model.user.CachedUser
import timber.log.Timber
import javax.inject.Inject

class CommentDbHelper @Inject constructor(private val vimeoDatabase: VimeoDatabase) {

    private val commentDao = vimeoDatabase.commentDao()
    private var userDao = vimeoDatabase.userDao()
    private var pictureSizesDao = vimeoDatabase.pictureSizesDao()

    suspend fun clear() = commentDao.clearComments()

    suspend fun insertComments(comments: List<CachedComment>) {
        Timber.d("insert comments")

        vimeoDatabase.withTransaction {
            for (comment in comments) {
                insertComment(comment)
            }
        }
    }

    private suspend fun insertComment(comment: CachedComment) {
        Timber.d("insert comment")
        val id = commentDao.insertComment(comment)

        comment.user?.also {
            it.commentId = id
            insertUser(it)
        }
    }

    private suspend fun insertUser(user: CachedUser) {
        Timber.d("insert user")
        val id = userDao.insertUser(user)

        user.pictureSizes.forEach {
            it.userId = id
        }
        user.pictureSizes.also {
            pictureSizesDao.insertPictureSizes(it)
        }
    }
}
