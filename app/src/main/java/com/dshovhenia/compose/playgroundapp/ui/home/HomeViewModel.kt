package com.dshovhenia.compose.playgroundapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dshovhenia.compose.playgroundapp.data.cache.model.video.RelationsVideo
import com.dshovhenia.compose.playgroundapp.data.repository.VideoRepository
import com.dshovhenia.compose.playgroundapp.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val videoRepository: VideoRepository
) : ViewModel() {

    var searchQuery: String? = null

    private val queryLiveData = MutableLiveData<Pair<String, String?>>()
    val videoResult: LiveData<PagingData<RelationsVideo>> = queryLiveData.switchMap { pair ->
        liveData {
            val repos = getResultStream(pair.first, pair.second)
            emitSource(repos)
        }
    }

    fun searchVideos(query: String) {
        searchQuery = query
        clearVideos()
        val pair = Pair(Constants.COLLECTION_URI, searchQuery)
        queryLiveData.postValue(pair)
    }

    fun showStaffPickVideos() {
        val pair = Pair(Constants.STAFF_PICKS_URI, null)
        queryLiveData.postValue(pair)
    }

    fun clearVideos() {
        videoRepository.clearVideos()
    }

    private fun getResultStream(initialUri: String, searchQuery: String?) =
        videoRepository.getVideos(initialUri, searchQuery).cachedIn(viewModelScope)

    companion object {
        const val NETWORK_PAGE_SIZE = 50
    }

}
