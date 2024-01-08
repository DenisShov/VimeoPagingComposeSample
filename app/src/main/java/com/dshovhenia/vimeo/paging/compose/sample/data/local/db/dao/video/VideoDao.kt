package com.dshovhenia.vimeo.paging.compose.sample.data.local.db.dao.video

import androidx.paging.PagingSource
import androidx.room.*
import com.dshovhenia.vimeo.paging.compose.sample.data.local.db.DbConstants
import com.dshovhenia.vimeo.paging.compose.sample.data.local.db.model.video.CachedVideo
import com.dshovhenia.vimeo.paging.compose.sample.data.local.db.model.video.RelationsVideo

@Dao
@Suppress("UnnecessaryAbstractClass")
abstract class VideoDao {

    @Transaction
    @Query("SELECT * FROM " + DbConstants.VIDEO_TABLE_NAME)
    abstract fun getVideos(): PagingSource<Int, RelationsVideo>

    @Transaction
    @Query("SELECT * FROM " + DbConstants.VIDEO_TABLE_NAME + " WHERE id = :videoId")
    abstract suspend fun getVideoById(videoId: Long): RelationsVideo

    @Query("DELETE FROM " + DbConstants.VIDEO_TABLE_NAME)
    abstract suspend fun clearVideos()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertVideo(cachedVideo: CachedVideo): Long

}
