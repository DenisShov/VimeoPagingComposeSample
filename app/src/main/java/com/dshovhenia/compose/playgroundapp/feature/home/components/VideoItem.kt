package com.dshovhenia.compose.playgroundapp.feature.home.components

import android.annotation.SuppressLint
import android.graphics.drawable.ColorDrawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.dshovhenia.compose.playgroundapp.R
import com.dshovhenia.compose.playgroundapp.data.mapper.relationsMapper.toCachedVideo
import com.dshovhenia.compose.playgroundapp.data.local.db.model.video.CachedVideo
import com.dshovhenia.compose.playgroundapp.feature.theme.ComposePlaygroundAppTheme
import com.dshovhenia.compose.playgroundapp.core.utils.TextUtil
import com.dshovhenia.compose.playgroundapp.core.utils.getRelationsVideo

@Composable
fun VideoItem(video: CachedVideo, onVideoClick: (CachedVideo) -> Unit = {}) {
    val context = LocalContext.current
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clickable { onVideoClick(video) }, elevation = 4.dp
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.fillMaxWidth()) {
                @Suppress("IMPLICIT_CAST_TO_ANY") val imageUrl =
                    if (video.pictureSizes.size > 2) {
                        video.pictureSizes[2].link
                    } else {
                        ColorDrawable(ContextCompat.getColor(context, android.R.color.darker_gray))
                    }
                AsyncImage(
                    model = imageUrl,
                    contentDescription = stringResource(R.string.image_description_video_image),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.popcorn),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
                Text(
                    text = TextUtil.formatSecondsToDuration(video.duration),
                    modifier = Modifier
                        .wrapContentWidth()
                        .background(colorResource(R.color.white), RectangleShape)
                        .align(Alignment.BottomEnd),
                    fontSize = 12.sp,
                )
            }
            Text(
                text = video.name,
                style = MaterialTheme.typography.h3,
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(start = 8.dp, top = 8.dp, end = 8.dp),
                color = colorResource(R.color.colorPrimaryText),
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = video.user?.name
                    ?: stringResource(R.string.user_name_placeholder),
                fontSize = 12.sp,
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(start = 8.dp),
                color = colorResource(R.color.colorPrimaryText),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painterResource(R.drawable.ic_favorite_red_11dp),
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(start = 8.dp, top = 6.dp, end = 3.dp),
                    contentDescription = stringResource(R.string.image_description_likes)
                )
                Text(
                    text = pluralStringResource(
                        R.plurals.video_feed_likes_plural,
                        video.likesTotal,
                        video.likesTotal
                    ),
                    fontSize = 12.sp,
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(top = 4.dp, end = 8.dp, bottom = 8.dp),
                )
            }
        }
    }
}

@SuppressLint("SimpleDateFormat")
@Preview(showBackground = true)
@Composable
fun VideoItemPreview() {
    ComposePlaygroundAppTheme {
        VideoItem(
            video = getRelationsVideo().toCachedVideo()
        )
    }
}

