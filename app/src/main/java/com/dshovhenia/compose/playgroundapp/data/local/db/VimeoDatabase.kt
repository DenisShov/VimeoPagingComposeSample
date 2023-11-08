package com.dshovhenia.compose.playgroundapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dshovhenia.compose.playgroundapp.data.local.db.dao.comment.CommentDao
import com.dshovhenia.compose.playgroundapp.data.local.db.dao.pictures.PictureSizesDao
import com.dshovhenia.compose.playgroundapp.data.local.db.dao.user.UserDao
import com.dshovhenia.compose.playgroundapp.data.local.db.dao.video.VideoDao
import com.dshovhenia.compose.playgroundapp.data.local.db.model.comment.CachedComment
import com.dshovhenia.compose.playgroundapp.data.local.db.model.pictures.CachedPictureSizes
import com.dshovhenia.compose.playgroundapp.data.local.db.model.user.CachedUser
import com.dshovhenia.compose.playgroundapp.data.local.db.model.video.CachedVideo

@Database(
  entities = [CachedComment::class, CachedVideo::class,
    CachedPictureSizes::class, CachedUser::class
  ], version = 1, exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class VimeoDatabase : RoomDatabase() {
  abstract fun commentDao(): CommentDao
  abstract fun videoDao(): VideoDao
  abstract fun userDao(): UserDao
  abstract fun pictureSizesDao(): PictureSizesDao
}
