package com.dshovhenia.compose.playgroundapp.data.local.preferences

import android.content.Context
import android.content.SharedPreferences
import com.dshovhenia.compose.playgroundapp.data.local.preferences.model.AccessToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PreferencesHelper @Inject constructor(@ApplicationContext context: Context) {

  private val sharedPreferences: SharedPreferences =
    context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)

  var accessToken: AccessToken
    get() {
      val accessToken = AccessToken()
      accessToken.token = sharedPreferences.getString(AccessToken.FIELD_ACCESS_TOKEN, "")!!
      accessToken.tokenType = sharedPreferences.getString(AccessToken.FIELD_TOKEN_TYPE, "")!!
      accessToken.scope = sharedPreferences.getString(AccessToken.FIELD_SCOPE, "")!!
      return accessToken
    }
    set(accessToken) {
      sharedPreferences.edit()
        .putString(AccessToken.FIELD_ACCESS_TOKEN, accessToken.token)
        .putString(AccessToken.FIELD_TOKEN_TYPE, accessToken.tokenType)
        .putString(AccessToken.FIELD_SCOPE, accessToken.scope)
        .apply()
    }

  fun deleteAccessToken() {
    sharedPreferences.edit()
      .remove(AccessToken.FIELD_ACCESS_TOKEN)
      .remove(AccessToken.FIELD_TOKEN_TYPE)
      .remove(AccessToken.FIELD_SCOPE)
      .apply()
  }

  fun clear() {
    sharedPreferences.edit().clear().apply()
  }

  companion object {
    private const val PREF_FILE_NAME = "vimeo_pref_file"
  }
}
