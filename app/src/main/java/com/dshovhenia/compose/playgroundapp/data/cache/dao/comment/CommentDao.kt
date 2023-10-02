package com.dshovhenia.compose.playgroundapp.data.cache.dao.comment

import androidx.paging.PagingSource
import androidx.room.*
import com.dshovhenia.compose.playgroundapp.data.cache.db.DbConstants
import com.dshovhenia.compose.playgroundapp.data.cache.model.comment.CachedComment
import com.dshovhenia.compose.playgroundapp.data.cache.model.comment.RelationsComment

@Dao
@Suppress("UnnecessaryAbstractClass")
abstract class CommentDao {

  @Transaction
  @Query("SELECT * FROM " + DbConstants.COMMENT_TABLE_NAME)
  abstract fun getComments(): PagingSource<Int, RelationsComment>

  @Query("DELETE FROM " + DbConstants.COMMENT_TABLE_NAME)
  abstract fun clearComments()

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  abstract fun insertComment(cachedComment: CachedComment): Long

}
