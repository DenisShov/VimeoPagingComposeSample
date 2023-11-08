package com.dshovhenia.compose.playgroundapp.data.remote.model.user

import android.os.Parcelable
import com.dshovhenia.compose.playgroundapp.data.remote.model.pictures.Pictures
import kotlinx.parcelize.Parcelize

@Parcelize
class User(
    var name: String = "", var pictures: Pictures?, var metadata: UserMetadata?
) : Parcelable
