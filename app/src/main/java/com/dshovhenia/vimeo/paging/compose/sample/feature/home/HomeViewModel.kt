package com.dshovhenia.vimeo.paging.compose.sample.feature.home

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dshovhenia.vimeo.paging.compose.sample.core.utils.Constants
import com.dshovhenia.vimeo.paging.compose.sample.data.local.db.model.video.RelationsVideo
import com.dshovhenia.vimeo.paging.compose.sample.domain.usecase.GetVideosUseCase
import com.dshovhenia.vimeo.paging.compose.sample.feature.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val getVideosUseCase: GetVideosUseCase
) : BaseViewModel() {

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    private var _videos = MutableStateFlow<PagingData<RelationsVideo>>(PagingData.empty())
    val videos = _videos.asStateFlow()

    var searchKeyword: String = ""

    fun searchKeywordVideos(keyword: String) {
        searchKeyword = keyword
        if (keyword.isBlank()) {
            getRecentVideos()
        } else {
            searchVideosByKeyword(keyword)
        }
    }

    private fun getRecentVideos() = viewModelScope.launch(dispatcher) {
        withContext(dispatcher) {
            getVideosUseCase.launch(Constants.STAFF_PICKS_URI, null)
                .cachedIn(viewModelScope)
                .collect {
                    _videos.value = it
                }
        }
    }

    private fun searchVideosByKeyword(keyword: String) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            getVideosUseCase.launch(Constants.COLLECTION_URI, keyword)
                .cachedIn(viewModelScope)
                .collect {
                    _videos.value = it
                }
        }
    }

    init {
        getRecentVideos()
    }
}
