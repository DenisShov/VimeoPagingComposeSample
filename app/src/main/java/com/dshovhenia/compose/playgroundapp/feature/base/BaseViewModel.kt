package com.dshovhenia.compose.playgroundapp.feature.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import com.dshovhenia.compose.playgroundapp.data.remote.functional.Errors

open class BaseViewModel : ViewModel() {

    private val _failure = MutableSharedFlow<Errors>()
    val failure = _failure.asSharedFlow()

    fun handleFailure(errors: Errors) {
        viewModelScope.launch {
            _failure.emit(errors)
        }
    }
}
