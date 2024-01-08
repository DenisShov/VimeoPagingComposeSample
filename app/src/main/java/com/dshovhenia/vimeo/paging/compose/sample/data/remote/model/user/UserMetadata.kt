package com.dshovhenia.vimeo.paging.compose.sample.data.remote.model.user

import android.os.Parcelable
import com.dshovhenia.vimeo.paging.compose.sample.data.remote.model.Connection
import kotlinx.parcelize.Parcelize

@Parcelize
class UserMetadata(
    var followersConnection: Connection? = null, var videosConnection: Connection? = null
) : Parcelable
