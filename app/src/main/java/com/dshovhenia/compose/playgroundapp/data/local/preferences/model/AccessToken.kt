package com.dshovhenia.compose.playgroundapp.data.local.preferences.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

class AccessToken {

    @SerializedName(FIELD_ACCESS_TOKEN)
    @Expose
    var token = ""

    @SerializedName(FIELD_TOKEN_TYPE)
    @Expose
    var tokenType = ""
        get() = // OAuth requires uppercase Authorization HTTP header value for token type
            if (field.isNotEmpty() && !Character.isUpperCase(field[0])) {
                firstLetterToUpperCase(field)
            } else field

    @SerializedName(FIELD_SCOPE)
    @Expose
    var scope = ""

    private fun firstLetterToUpperCase(string: String) =
        string[0].toString().uppercase(Locale.getDefault()) + string.substring(1)

    val authorizationHeader: String
        get() = if (tokenType.isEmpty()) "" else "$tokenType $token"

    override fun toString(): String {
        return "AccessToken:\n- access_token: $token\n- token_type: $tokenType\n- scope: $scope\n"
    }

    companion object {
        const val FIELD_ACCESS_TOKEN = "access_token"
        const val FIELD_TOKEN_TYPE = "token_type"
        const val FIELD_SCOPE = "scope"
    }
}
