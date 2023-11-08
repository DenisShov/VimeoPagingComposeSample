package com.dshovhenia.compose.playgroundapp.data.local.db.dao.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dshovhenia.compose.playgroundapp.data.local.db.DbConstants
import com.dshovhenia.compose.playgroundapp.data.local.db.model.user.CachedUser
import kotlinx.coroutines.flow.Flow

@Dao
@Suppress("UnnecessaryAbstractClass")
abstract class UserDao {

    @Query("SELECT * FROM " + DbConstants.USER_TABLE_NAME)
    abstract fun getUsers(): Flow<List<CachedUser>>

    @Query("DELETE FROM " + DbConstants.USER_TABLE_NAME)
    abstract suspend fun clearUsers()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertUser(cachedVideo: CachedUser): Long

}
