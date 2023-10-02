package com.dshovhenia.compose.playgroundapp.ui.details.comments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dshovhenia.compose.playgroundapp.data.cache.model.comment.RelationsComment
import com.dshovhenia.compose.playgroundapp.data.repository.CommentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(
    private val commentRepository: CommentRepository
) : ViewModel() {

    companion object {
        const val NETWORK_PAGE_SIZE = 50
    }

    var commentUrl: String? = null

    private val queryLiveData = MutableLiveData<String>()
    val commentResult: LiveData<PagingData<RelationsComment>> =
        queryLiveData.switchMap { initialUri ->
            liveData {
                val repos = getResultStream(initialUri)
                emitSource(repos)
            }
        }

    private fun getResultStream(initialUri: String) =
        commentRepository.getComments(initialUri).cachedIn(viewModelScope)

    fun onInitialListRequested(url: String) {
        commentUrl = url
        commentRepository.clearComments()
        queryLiveData.postValue(url)
    }
}
