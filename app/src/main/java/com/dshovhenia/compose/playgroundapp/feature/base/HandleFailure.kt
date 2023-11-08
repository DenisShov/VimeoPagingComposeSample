package com.dshovhenia.compose.playgroundapp.feature.base

import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.dshovhenia.compose.playgroundapp.R
import com.dshovhenia.compose.playgroundapp.data.remote.functional.Errors
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

@Composable
fun HandleFailure(failure: SharedFlow<Errors>) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        failure.collect { errorEvent ->
            val errorsMessage = when (errorEvent) {
                is Errors.MissingNetworkConnection -> context.resources.getString(R.string.no_network)
                is Errors.ApiErrors -> context.resources.getString(R.string.some_api_error)
                is Errors.GeneralErrors -> "${context.resources.getString(R.string.faced_an_error)} ${errorEvent.exception}"
            }

            scope.launch {
                snackbarHostState.showSnackbar(
                    message = errorsMessage,
                    duration = SnackbarDuration.Long
                )
            }
        }
    }
}