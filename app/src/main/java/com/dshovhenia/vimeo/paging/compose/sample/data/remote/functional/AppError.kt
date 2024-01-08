package com.dshovhenia.vimeo.paging.compose.sample.data.remote.functional

import java.io.IOException
import java.net.ConnectException
import java.net.UnknownHostException

sealed class AppError {
    object MissingNetworkConnection : AppError()
    data class ApiError(val exception: IOException) : AppError()
    data class GeneralError(val exception: Throwable) : AppError()
}

@SuppressWarnings("TooGenericExceptionCaught")
inline fun <T> wrapResult(block: () -> T): Result<T, AppError> {
    return try {
        Result.success(block())
    } catch (exception: UnknownHostException) {
        Result.failure(AppError.MissingNetworkConnection)
    } catch (exception: ConnectException) {
        Result.failure(AppError.MissingNetworkConnection)
    } catch (exception: IOException) {
        Result.failure(AppError.ApiError(exception))
    } catch (exception: Exception) {
        Result.failure(AppError.GeneralError(exception))
    }
}
