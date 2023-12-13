package com.dshovhenia.compose.playgroundapp.feature.base

import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.dshovhenia.compose.playgroundapp.R
import com.dshovhenia.compose.playgroundapp.data.remote.functional.AppError
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

@Composable
fun HandleFailure(failure: SharedFlow<AppError>) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        failure.collect { errorEvent ->
            val appErrorMessage = when (errorEvent) {
                is AppError.MissingNetworkConnection -> context.resources.getString(R.string.no_network)
                is AppError.ApiError -> context.resources.getString(R.string.some_api_error)
                is AppError.GeneralError -> "${context.resources.getString(R.string.faced_an_error)} ${errorEvent.exception}"
            }

            scope.launch {
                snackbarHostState.showSnackbar(
                    message = appErrorMessage,
                    duration = SnackbarDuration.Long
                )
            }
        }
    }
}