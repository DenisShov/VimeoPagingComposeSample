package com.dshovhenia.compose.playgroundapp.data.model.user

import android.os.Parcelable
import com.dshovhenia.compose.playgroundapp.data.model.pictures.Pictures
import kotlinx.parcelize.Parcelize

@Parcelize
class User(
    var name: String = "", var pictures: Pictures?, var metadata: UserMetadata?
) : Parcelable
