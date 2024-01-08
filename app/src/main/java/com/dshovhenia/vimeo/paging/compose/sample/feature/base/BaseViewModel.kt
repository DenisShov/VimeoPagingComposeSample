package com.dshovhenia.vimeo.paging.compose.sample.feature.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import com.dshovhenia.vimeo.paging.compose.sample.data.remote.functional.AppError

open class BaseViewModel : ViewModel() {

    private val _failure = MutableSharedFlow<AppError>()
    val failure = _failure.asSharedFlow()

    fun handleFailure(appError: AppError) {
        viewModelScope.launch {
            _failure.emit(appError)
        }
    }
}
