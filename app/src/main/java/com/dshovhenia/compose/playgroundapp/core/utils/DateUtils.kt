package com.dshovhenia.compose.playgroundapp.core.utils

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("SimpleDateFormat")
fun String.parseDate(pattern: String): Date? {
    val format = SimpleDateFormat(pattern)
    var createdTime: Date? = null
    try {
        createdTime = format.parse(this)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return createdTime
}