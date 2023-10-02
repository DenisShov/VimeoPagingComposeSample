package com.dshovhenia.compose.playgroundapp.data.model.pictures

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Pictures(var sizes: List<PictureSizes> = ArrayList()) : Parcelable
