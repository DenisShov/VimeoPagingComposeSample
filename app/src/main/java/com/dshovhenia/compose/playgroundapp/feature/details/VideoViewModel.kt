package com.dshovhenia.compose.playgroundapp.feature.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dshovhenia.compose.playgroundapp.data.local.db.model.comment.RelationsComment
import com.dshovhenia.compose.playgroundapp.data.local.db.model.video.CachedVideo
import com.dshovhenia.compose.playgroundapp.data.mapper.relationsMapper.toCachedVideo
import com.dshovhenia.compose.playgroundapp.domain.usecase.GetCommentsUseCase
import com.dshovhenia.compose.playgroundapp.domain.usecase.GetVideoByIdUseCase
import com.dshovhenia.compose.playgroundapp.feature.base.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VideoViewModel @AssistedInject constructor(
    @Assisted private val videoId: Long,
    @Assisted private val commentUrl: String,
    private val getVideoByIdUseCase: GetVideoByIdUseCase,
    private val getCommentsUseCase: GetCommentsUseCase,
) : BaseViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(videoId: Long, commentUrl: String): VideoViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun provideFactory(
            assistedFactory: Factory, videoId: Long, commentUrl: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(videoId, commentUrl) as T
            }
        }
    }

    private val _video = MutableStateFlow<CachedVideo?>(null)
    val video = _video.asStateFlow()

    private val _comments = MutableStateFlow<PagingData<RelationsComment>>(PagingData.empty())
    val comments = _comments.asStateFlow()

    private fun getVideoByIdFlow(videoId: Long) {
        viewModelScope.launch {
            getVideoByIdUseCase.launch(videoId).collect {
                _video.value = it.toCachedVideo()
            }
        }
    }

    private fun getComments(commentUrl: String) {
        viewModelScope.launch {
            getCommentsUseCase.launch(commentUrl)
                .cachedIn(viewModelScope)
                .collect {
                    _comments.value = it
                }
        }
    }

    init {
        getVideoByIdFlow(videoId)
        getComments(commentUrl)
    }
}
