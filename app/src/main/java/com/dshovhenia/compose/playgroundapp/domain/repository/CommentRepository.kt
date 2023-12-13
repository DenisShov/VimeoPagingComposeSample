package com.dshovhenia.compose.playgroundapp.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.dshovhenia.compose.playgroundapp.data.local.db.VimeoDatabase
import com.dshovhenia.compose.playgroundapp.data.local.db.helper.CommentDbHelper
import com.dshovhenia.compose.playgroundapp.data.paging.comments.CommentRemoteMediator
import com.dshovhenia.compose.playgroundapp.data.remote.service.VimeoApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OptIn(androidx.paging.ExperimentalPagingApi::class)
class CommentRepository @Inject constructor(
    val vimeoDatabase: VimeoDatabase,
    val commentDbHelper: CommentDbHelper,
    val vimeoApiService: VimeoApiService
) {

    fun getCommentsFlow(initialUri: String) =
        Pager(config = PagingConfig(pageSize = NETWORK_PAGE_SIZE),
            remoteMediator = CommentRemoteMediator(
                initialUri, commentDbHelper, vimeoApiService, NETWORK_PAGE_SIZE
            ),
            pagingSourceFactory = { vimeoDatabase.commentDao().getComments() }).flow

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }
}
