package com.dshovhenia.vimeo.paging.compose.sample.data.remote.model.user

import android.os.Parcelable
import com.dshovhenia.vimeo.paging.compose.sample.data.remote.model.pictures.Pictures
import kotlinx.parcelize.Parcelize

@Parcelize
class User(
    var name: String = "", var pictures: Pictures?, var metadata: UserMetadata?
) : Parcelable
