package com.dshovhenia.compose.playgroundapp.data.remote.authentication

import com.dshovhenia.compose.playgroundapp.data.cache.preferences.PreferencesHelper
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class AuthenticationInterceptor @Inject constructor(private val mPreferencesHelper: PreferencesHelper) :
  Interceptor {

  @Throws(IOException::class)
  override fun intercept(chain: Chain): Response {
    val accessToken = mPreferencesHelper.accessToken
    val original = chain.request()

    Timber.i(accessToken.authorizationHeader)

    // If we already have an Authorization header, it's a basic Authorization header from when
    // VimeoServiceAuthenticator tries to generate a basic token that we don't yet have. Therefore,
    // if we continue to build a newRequest and add another Authorization header, accessToken.getAuthorizationHeader()
    // will return "". Adding this header with the basic header from VimeoServiceAuthenticator will result
    // in an http response of 400 Bad Request, so we have to check here if the Authorization header is already set,
    // and continue as is, since we need to get the unauthenticated token.
    if (original.headers(KEY_AUTHORIZATION).size > 0) {
      return chain.proceed(original)
    }

    // set or override the 'Authorization' header - keep the request body
    val newRequest = original.newBuilder().header(KEY_AUTHORIZATION, accessToken.authorizationHeader)
        .method(original.method(), original.body()).build()
    return chain.proceed(newRequest)
  }

  companion object {
    private const val KEY_AUTHORIZATION = "Authorization"
  }

}
