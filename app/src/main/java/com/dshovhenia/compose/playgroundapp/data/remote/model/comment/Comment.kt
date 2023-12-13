package com.dshovhenia.compose.playgroundapp.data.remote.model.comment

import android.os.Parcelable
import com.dshovhenia.compose.playgroundapp.data.remote.model.user.User
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Comment(
    var uri: String = "",
    var text: String = "",
    @SerializedName("created_on")
    var createdOn: Date? = null,
    var nextPage: String? = null,
    var user: User? = null
) : Parcelable
