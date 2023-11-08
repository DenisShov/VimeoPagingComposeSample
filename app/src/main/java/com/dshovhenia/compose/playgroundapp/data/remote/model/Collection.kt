package com.dshovhenia.compose.playgroundapp.data.remote.model

import android.os.Parcelable

import java.util.*

class Collection<T : Parcelable>(
    var paging: Paging? = null,
    var data: List<T> = ArrayList()
)
