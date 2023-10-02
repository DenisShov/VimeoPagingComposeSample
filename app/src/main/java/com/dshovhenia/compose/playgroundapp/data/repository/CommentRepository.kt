package com.dshovhenia.compose.playgroundapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.dshovhenia.compose.playgroundapp.data.cache.db.VimeoDatabase
import com.dshovhenia.compose.playgroundapp.data.cache.helper.CommentDbHelper
import com.dshovhenia.compose.playgroundapp.data.cache.mapper.comment.CommentMapper
import com.dshovhenia.compose.playgroundapp.data.remote.service.VimeoApiService
import com.dshovhenia.compose.playgroundapp.paging.comments.CommentRemoteMediator
import com.dshovhenia.compose.playgroundapp.ui.details.comments.CommentsViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OptIn(androidx.paging.ExperimentalPagingApi::class)
class CommentRepository @Inject constructor(
    val vimeoDatabase: VimeoDatabase,
    val commentDbHelper: CommentDbHelper,
    val commentMapper: CommentMapper,
    val vimeoApiService: VimeoApiService
) {

  fun getComments(initialUri: String) =
    Pager(
      config = PagingConfig(pageSize = CommentsViewModel.NETWORK_PAGE_SIZE),
      remoteMediator = CommentRemoteMediator(
        initialUri,
        commentDbHelper,
        commentMapper,
        vimeoApiService
      ),
      pagingSourceFactory = { vimeoDatabase.commentDao().getComments() }
    ).liveData

  fun clearComments() = commentDbHelper.clear()
}
