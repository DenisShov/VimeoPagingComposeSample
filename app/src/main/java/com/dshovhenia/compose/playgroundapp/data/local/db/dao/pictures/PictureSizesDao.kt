package com.dshovhenia.compose.playgroundapp.data.local.db.dao.pictures

import androidx.room.*
import com.dshovhenia.compose.playgroundapp.data.local.db.DbConstants
import com.dshovhenia.compose.playgroundapp.data.local.db.model.pictures.CachedPictureSizes
import kotlinx.coroutines.flow.Flow

@Dao
@Suppress("UnnecessaryAbstractClass")
abstract class PictureSizesDao {

    @Query("SELECT * FROM " + DbConstants.PICTURE_SIZES_TABLE_NAME)
    abstract fun getPictureSizes(): Flow<List<CachedPictureSizes>>

    @Query("DELETE FROM " + DbConstants.PICTURE_SIZES_TABLE_NAME)
    abstract suspend fun clearPictureSizes()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertPictureSizes(cachedPictureSizes: CachedPictureSizes): Long

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertPictureSizes(cachedPictureSizes: List<CachedPictureSizes>)

}
