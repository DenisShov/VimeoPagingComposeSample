package com.dshovhenia.vimeo.paging.compose.sample.data.remote.model

import android.os.Parcelable

import java.util.*

class Collection<T : Parcelable>(
    var paging: Paging? = null,
    var data: List<T> = ArrayList()
)
