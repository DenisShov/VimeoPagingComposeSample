package com.dshovhenia.vimeo.paging.compose.sample.data.local.db.dao.comment

import androidx.paging.PagingSource
import androidx.room.*
import com.dshovhenia.vimeo.paging.compose.sample.data.local.db.DbConstants
import com.dshovhenia.vimeo.paging.compose.sample.data.local.db.model.comment.CachedComment
import com.dshovhenia.vimeo.paging.compose.sample.data.local.db.model.comment.RelationsComment

@Dao
@Suppress("UnnecessaryAbstractClass")
abstract class CommentDao {

  @Transaction
  @Query("SELECT * FROM " + DbConstants.COMMENT_TABLE_NAME)
  abstract fun getComments(): PagingSource<Int, RelationsComment>

  @Query("DELETE FROM " + DbConstants.COMMENT_TABLE_NAME)
  abstract suspend fun clearComments()

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  abstract suspend fun insertComment(cachedComment: CachedComment): Long

}
