package com.dshovhenia.compose.playgroundapp.data.remote.functional

import java.io.IOException
import java.net.ConnectException
import java.net.UnknownHostException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

sealed class Errors {
    object MissingNetworkConnection : Errors()
    data class ApiErrors(val exception: IOException) : Errors()
    data class GeneralErrors(val exception: Throwable) : Errors()
}