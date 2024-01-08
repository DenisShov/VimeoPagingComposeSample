package com.dshovhenia.vimeo.paging.compose.sample.data.remote.authentication

import android.util.Base64
import com.dshovhenia.vimeo.paging.compose.sample.BuildConfig
import com.dshovhenia.vimeo.paging.compose.sample.data.local.preferences.PreferencesHelper
import com.dshovhenia.vimeo.paging.compose.sample.data.local.preferences.model.AccessToken
import com.dshovhenia.vimeo.paging.compose.sample.data.remote.service.VimeoApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber
import dagger.Lazy
import javax.inject.Inject

class VimeoServiceAuthenticator @Inject constructor(
    // Lazy computes its value on the first call to get() and remembers that same value for all subsequent calls to get().
    val vimeoApiService: Lazy<VimeoApiService>,
    val prefHelper: PreferencesHelper
) : Authenticator {

    private var newAccessToken: AccessToken? = null

    override fun authenticate(route: Route?, response: Response): Request? {
        Timber.i("calling authenticator - responseCount: %s", responseCount(response))
        // If we’ve failed 2 times, give up.
        if (responseCount(response) >= 2) {
            return null
        }

        // Get the new Unauthenticated access token
        runBlocking(Dispatchers.IO) {
            launch {
                runCatching {
                    newAccessToken = vimeoApiService.get()
                        .getUnauthenticatedToken(
                            basicAuthorizationHeader()
                        )
                }.onSuccess {
                    Timber.i("Access token received successfully")
                }.onFailure {
                    Timber.e("Failed to get access token")
                }
            }
        }

        var newRequest: Request? = null

        newAccessToken?.let {
            newRequest = response.request.newBuilder()
                .header("Authorization", it.authorizationHeader).build()
            // save the newAccessToken in SharedPref
            prefHelper.accessToken = it
        } ?: Timber.e("Failed to get Unauthenticated token")

        return newRequest
    }

    private fun responseCount(response: Response?): Int {
        var res = response
        var result = 1
        while ((res?.priorResponse.also { res = it }) != null) {
            result++
        }
        return result
    }

    private fun basicAuthorizationHeader() = "basic " + Base64.encodeToString(
        "${BuildConfig.CLIENT_ID}:${BuildConfig.CLIENT_SECRET}".toByteArray(),
        Base64.NO_WRAP
    )
}
