package com.dshovhenia.vimeo.paging.compose.sample.feature.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dshovhenia.vimeo.paging.compose.sample.data.local.db.model.comment.RelationsComment
import com.dshovhenia.vimeo.paging.compose.sample.data.local.db.model.video.CachedVideo
import com.dshovhenia.vimeo.paging.compose.sample.data.mapper.relationsMapper.toCachedVideo
import com.dshovhenia.vimeo.paging.compose.sample.domain.usecase.GetCommentsUseCase
import com.dshovhenia.vimeo.paging.compose.sample.domain.usecase.GetVideoByIdUseCase
import com.dshovhenia.vimeo.paging.compose.sample.feature.base.BaseViewModel
import com.dshovhenia.vimeo.paging.compose.sample.feature.ARG_COMMENTS_URL
import com.dshovhenia.vimeo.paging.compose.sample.feature.ARG_VIDEO_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val dispatcher: CoroutineDispatcher,
    private val getVideoByIdUseCase: GetVideoByIdUseCase,
    private val getCommentsUseCase: GetCommentsUseCase,
) : BaseViewModel() {

    private val videoId: Long = checkNotNull(savedStateHandle[ARG_VIDEO_ID])
    private val commentUrl: String = checkNotNull(savedStateHandle[ARG_COMMENTS_URL])

    private val _video = MutableStateFlow<CachedVideo?>(null)
    val video = _video.asStateFlow()

    private val _comments = MutableStateFlow<PagingData<RelationsComment>>(PagingData.empty())
    val comments = _comments.asStateFlow()

    private fun getVideoByIdFlow(videoId: Long) {
        viewModelScope.launch(dispatcher) {
            withContext(dispatcher) {
                getVideoByIdUseCase.launch(videoId)
            }.fold(
                onSuccess = {
                    _video.value = it.toCachedVideo()
                },
                onFailure = ::handleFailure
            )
        }
    }

    private fun getComments(commentUrl: String) {
        viewModelScope.launch {
            withContext(dispatcher) {
                getCommentsUseCase.launch(commentUrl)
                    .cachedIn(viewModelScope)
                    .collect {
                        _comments.value = it
                    }
            }
        }
    }

    init {
        getVideoByIdFlow(videoId)
        getComments(commentUrl)
    }
}
