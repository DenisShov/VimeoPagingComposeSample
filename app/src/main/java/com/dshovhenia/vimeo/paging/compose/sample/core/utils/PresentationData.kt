package com.dshovhenia.vimeo.paging.compose.sample.core.utils

import com.dshovhenia.vimeo.paging.compose.sample.data.local.db.model.comment.CachedComment
import com.dshovhenia.vimeo.paging.compose.sample.data.local.db.model.comment.RelationsComment
import com.dshovhenia.vimeo.paging.compose.sample.data.local.db.model.pictures.CachedPictureSizes
import com.dshovhenia.vimeo.paging.compose.sample.data.local.db.model.user.CachedUser
import com.dshovhenia.vimeo.paging.compose.sample.data.local.db.model.user.RelationsUser
import com.dshovhenia.vimeo.paging.compose.sample.data.local.db.model.video.CachedVideo
import com.dshovhenia.vimeo.paging.compose.sample.data.local.db.model.video.RelationsVideo

fun getRelationsVideo() = RelationsVideo(
    video = getCachedVideo(),
    relationsUser = getRelationsUser(),
    relationsPictureSizes = cachedVideoPictureSizes()
)

fun getRelationsUser(): RelationsUser {
    return RelationsUser(user = getCachedUser(), pictureSizes = cachedUserPictureSizes())
}

fun getCachedUser() = CachedUser(
    commentId = null,
    videoId = null,
    name = "Aisha",
    followersUri = "/users/28286355/followers",
    followersTotal = 85,
    videosUri = "/users/28286355/videos",
    videosTotal = 4,
    pictureSizes = cachedUserPictureSizes()
)

fun getRelationsComment() = RelationsComment(
    comment = getCachedComment(),
    relationsUser = getRelationsUser()
)

private fun getCachedComment(): CachedComment {
    return CachedComment(
        uri = "/videos/872349418/comments/20376297",
        text = "Some comment",
        created_on = "2023-10-11".parseDate("yyyy-MM-dd"),
    )
}

fun getCachedVideo(): CachedVideo {
    return CachedVideo(
        uri = "/videos/820880527",
        name = "Some video name",
        description = "Some video description",
        duration = 526,
        createdTime = "2023-04-25".parseDate("yyyy-MM-dd"),
        commentsUri = "/videos/820880527/comments",
        commentsTotal = 17,
        likesUri = "/videos/820880527/likes",
        likesTotal = 202,
        videoPlays = 555884
    )
}

private fun cachedVideoPictureSizes(): MutableList<CachedPictureSizes> {
    val userCachedPictureSizes540 = CachedPictureSizes()
    userCachedPictureSizes540.run {
        width = 960
        height = 540
        link = "https://i.vimeocdn.com/video/1660827257-d2c00d3eaaf3e210d5" +
                "9d56326927ec0b5afe05ff1667ece93b746ee0c89ee985-d_960x540?r=pad"
    }
    val userCachedPictureSizes720 = CachedPictureSizes()
    userCachedPictureSizes720.run {
        width = 1280
        height = 720
        link = "https://i.vimeocdn.com/video/1660827257-d2c00d3eaaf3e210d59d5" +
                "6326927ec0b5afe05ff1667ece93b746ee0c89ee985-d_1280x720?r=pad"
    }
    return mutableListOf(userCachedPictureSizes540, userCachedPictureSizes720)
}

private fun cachedUserPictureSizes(): MutableList<CachedPictureSizes> {
    val userCachedPictureSizes300 = CachedPictureSizes()
    userCachedPictureSizes300.run {
        width = 300
        height = 300
        link = "https://i.vimeocdn.com/portrait/86266625_" +
                "300x300?subrect=31%2C219%2C879%2C1067&r=cover"
    }
    val userCachedPictureSizes360 = CachedPictureSizes()
    userCachedPictureSizes360.run {
        width = 360
        height = 360
        link = "https://i.vimeocdn.com/portrait/86266625_" +
                "360x360?subrect=31%2C219%2C879%2C1067&r=cover"
    }
    return mutableListOf(userCachedPictureSizes300, userCachedPictureSizes360)
}
