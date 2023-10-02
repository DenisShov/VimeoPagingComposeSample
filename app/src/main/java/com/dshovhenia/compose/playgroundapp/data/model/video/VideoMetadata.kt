package com.dshovhenia.compose.playgroundapp.data.model.video

import android.os.Parcelable
import com.dshovhenia.compose.playgroundapp.data.model.Connection
import kotlinx.parcelize.Parcelize

@Parcelize
class VideoMetadata(
  var commentsConnection: Connection? = null, var likesConnection: Connection? = null
) : Parcelable
