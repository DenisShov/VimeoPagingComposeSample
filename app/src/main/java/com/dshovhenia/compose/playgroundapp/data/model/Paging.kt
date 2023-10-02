package com.dshovhenia.compose.playgroundapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Paging(
    var next: String?
) : Parcelable
