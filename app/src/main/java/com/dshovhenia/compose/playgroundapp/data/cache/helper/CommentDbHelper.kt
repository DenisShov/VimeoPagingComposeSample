package com.dshovhenia.compose.playgroundapp.data.cache.helper

import com.dshovhenia.compose.playgroundapp.data.cache.db.VimeoDatabase
import com.dshovhenia.compose.playgroundapp.data.cache.model.comment.CachedComment
import com.dshovhenia.compose.playgroundapp.data.cache.model.user.CachedUser
import timber.log.Timber
import javax.inject.Inject

class CommentDbHelper @Inject constructor(private val vimeoDatabase: VimeoDatabase) {

    private val commentDao = vimeoDatabase.commentDao()
    private var userDao = vimeoDatabase.userDao()
    private var pictureSizesDao = vimeoDatabase.pictureSizesDao()

    fun clear() {
        commentDao.clearComments()
    }

    fun insertComments(comments: List<CachedComment>) {
        Timber.d("insert comments")

        vimeoDatabase.runInTransaction {
            for (comment in comments) {
                insertComment(comment)
            }
        }
    }

    private fun insertComment(comment: CachedComment) {
        Timber.d("insert comment")
        val id = commentDao.insertComment(comment)

        comment.user?.also {
            it.commentId = id
            insertUser(it)
        }
    }

    private fun insertUser(user: CachedUser) {
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
