package com.dshovhenia.vimeo.paging.compose.sample.data.remote.model.video

import android.os.Parcelable
import com.dshovhenia.vimeo.paging.compose.sample.data.remote.model.pictures.Pictures
import com.dshovhenia.vimeo.paging.compose.sample.data.remote.model.user.User
import com.dshovhenia.vimeo.paging.compose.sample.core.utils.parceler.DateParceler
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.TypeParceler
import java.util.*

@Parcelize
@TypeParceler<Date, DateParceler>
@Suppress("LongParameterList")
class Video(
    var uri: String = "",
    var name: String = "",
    var description: String?,
    var duration: Int = 0,
    @SerializedName("created_time")
    var createdTime: Date?,
    var nextPage: String? = null,
    var user: User?,
    var pictures: Pictures?,
    var metadata: VideoMetadata?,
    var stats: VideoStats?
) : Parcelable
