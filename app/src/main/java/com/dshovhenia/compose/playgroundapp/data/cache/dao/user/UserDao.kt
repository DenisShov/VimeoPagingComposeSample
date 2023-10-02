package com.dshovhenia.compose.playgroundapp.data.cache.dao.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dshovhenia.compose.playgroundapp.data.cache.db.DbConstants
import com.dshovhenia.compose.playgroundapp.data.cache.model.user.CachedUser

@Dao
@Suppress("UnnecessaryAbstractClass")
abstract class UserDao {

  @Query("SELECT * FROM " + DbConstants.USER_TABLE_NAME)
  abstract fun getUsers(): List<CachedUser>

  @Query("DELETE FROM " + DbConstants.USER_TABLE_NAME)
  abstract fun clearUsers()

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  abstract fun insertUser(cachedVideo: CachedUser) : Long

}
