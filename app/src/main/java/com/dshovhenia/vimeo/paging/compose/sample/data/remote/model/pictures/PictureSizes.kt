package com.dshovhenia.vimeo.paging.compose.sample.data.remote.model.pictures

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class PictureSizes(
  var width: Int = 0, var height: Int = 0, var link: String = ""
) : Parcelable
