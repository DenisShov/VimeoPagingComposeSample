package com.dshovhenia.compose.playgroundapp.data.cache.dao.video

import androidx.paging.PagingSource
import androidx.room.*
import com.dshovhenia.compose.playgroundapp.data.cache.db.DbConstants
import com.dshovhenia.compose.playgroundapp.data.cache.model.video.CachedVideo
import com.dshovhenia.compose.playgroundapp.data.cache.model.video.RelationsVideo

@Dao
@Suppress("UnnecessaryAbstractClass")
abstract class VideoDao {

  @Transaction
  @Query("SELECT * FROM " + DbConstants.VIDEO_TABLE_NAME)
  abstract fun getVideos(): PagingSource<Int, RelationsVideo>

  @Transaction
  @Query("SELECT * FROM " + DbConstants.VIDEO_TABLE_NAME + " WHERE id = :videoId")
  abstract fun getVideoById(videoId: Long): RelationsVideo

  @Query("DELETE FROM " + DbConstants.VIDEO_TABLE_NAME)
  abstract fun clearVideos()

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  abstract fun insertVideo(cachedVideo: CachedVideo): Long

}
