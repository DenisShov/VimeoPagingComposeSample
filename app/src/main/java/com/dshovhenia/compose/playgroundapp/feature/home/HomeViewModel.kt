package com.dshovhenia.compose.playgroundapp.feature.home

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dshovhenia.compose.playgroundapp.core.utils.Constants
import com.dshovhenia.compose.playgroundapp.data.local.db.model.video.RelationsVideo
import com.dshovhenia.compose.playgroundapp.domain.usecase.GetVideosUseCase
import com.dshovhenia.compose.playgroundapp.feature.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
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

    private fun getRecentVideos() = viewModelScope.launch {
        getVideosUseCase.launch(Constants.STAFF_PICKS_URI, null)
            .cachedIn(viewModelScope)
            .collect {
                _videos.value = it
            }
    }

    private fun searchVideosByKeyword(keyword: String) = viewModelScope.launch {
        getVideosUseCase.launch(Constants.COLLECTION_URI, keyword)
            .cachedIn(viewModelScope)
            .collect {
                _videos.value = it
            }
    }

    init {
        getRecentVideos()
    }
}
