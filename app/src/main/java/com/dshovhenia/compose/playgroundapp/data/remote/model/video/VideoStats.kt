package com.dshovhenia.compose.playgroundapp.data.remote.model.video

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class VideoStats(var plays: Long = 0) : Parcelable
