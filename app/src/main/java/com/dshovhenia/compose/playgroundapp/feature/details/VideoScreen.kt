package com.dshovhenia.compose.playgroundapp.feature.details

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.util.DisplayMetrics
import android.webkit.CookieManager
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.dshovhenia.compose.playgroundapp.R
import com.dshovhenia.compose.playgroundapp.core.utils.DisplayMetricsUtil
import com.dshovhenia.compose.playgroundapp.core.utils.TextUtil
import com.dshovhenia.compose.playgroundapp.data.local.db.model.comment.RelationsComment
import com.dshovhenia.compose.playgroundapp.data.local.db.model.user.CachedUser
import com.dshovhenia.compose.playgroundapp.data.local.db.model.video.CachedVideo
import com.dshovhenia.compose.playgroundapp.feature.base.HandleFailure
import com.dshovhenia.compose.playgroundapp.feature.details.components.CommentItem
import com.dshovhenia.compose.playgroundapp.feature.details.components.ExpandableText
import com.dshovhenia.compose.playgroundapp.feature.theme.ComposePlaygroundAppTheme
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState

@Preview(showBackground = true)
@Composable
fun VideoScreenPreview() {
    ComposePlaygroundAppTheme {
        VideoScreen(viewModel = viewModel())
    }
}

@Composable
fun VideoScreen(viewModel: VideoViewModel) {
    HandleFailure(viewModel.failure)

    val video by viewModel.video.collectAsState()
    val comments = viewModel.comments.collectAsLazyPagingItems()

    video?.let { VideoDetailContent(it, comments) }
}

@Composable
fun VideoDetailContent(video: CachedVideo, comments: LazyPagingItems<RelationsComment>) {
    Column(
        modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Box(modifier = Modifier.fillMaxWidth()) {
                    VideoWebView(video)
                    Text(
                        text = TextUtil.formatSecondsToDuration(video.duration),
                        modifier = Modifier
                            .wrapContentWidth()
                            .background(colorResource(R.color.dimFilter), RectangleShape)
                            .align(Alignment.BottomEnd),
                        fontSize = 12.sp,
                    )
                }
                Text(
                    text = video.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 8.dp, end = 16.dp),
                    color = colorResource(R.color.colorPrimaryText),
                    fontSize = 18.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = TextUtil.formatVideoAgeAndPlays(video.videoPlays, video.createdTime),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                    fontSize = 12.sp,
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Divider(color = Color.Gray, thickness = 1.dp)
                ExpandableText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp),
                    style = TextStyle(
                        color = colorResource(R.color.colorPrimaryText),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Start
                    ),
                    text = video.description,
                    showMoreStyle = SpanStyle(
                        fontWeight = FontWeight.W500,
                        color = colorResource(R.color.colorPrimary)
                    )
                )
                video.user?.let {
                    UserItem(it)
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(colorResource(R.color.colorPrimary), RectangleShape)
                ) {
                    Text(
                        text = stringResource(R.string.comments),
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(8.dp),
                        color = colorResource(R.color.colorPrimaryText),
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                    )
                }
            }

            if (comments.loadState.refresh is LoadState.Loading) {
                item {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(16.dp)
                    )
                }
            } else {
                items(count = comments.itemCount) { index ->
                    comments[index]?.let {
                        CommentItem(comment = it)
                    }
                }
                item {
                    if (comments.loadState.append is LoadState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .wrapContentWidth()
                                .padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun VideoWebView(video: CachedVideo) {
    val context = LocalContext.current

    val videoUrl = "https://player.vimeo.com/video/" + TextUtil.generateIdFromUri(video.uri)
    val webViewState = rememberWebViewState(url = videoUrl)
    val accompanistWebViewClient = remember { getAccompanistWebViewClient(videoUrl) }

    WebView(
        modifier = Modifier
            .fillMaxWidth()
            .height(getVideoWebViewHeight(context)),
        state = webViewState,
        captureBackPresses = true,
        onCreated = { it: WebView ->
            it.settings.javaScriptEnabled = true
            it.settings.javaScriptCanOpenWindowsAutomatically = true
            it.settings.mediaPlaybackRequiresUserGesture = false
            it.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            CookieManager.getInstance().setAcceptThirdPartyCookies(it, true)
        },
        client = accompanistWebViewClient
    )
}

@Composable
fun UserItem(user: CachedUser) {
    Divider(color = Color.Gray, thickness = 1.dp)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        @Suppress("IMPLICIT_CAST_TO_ANY") val userPhotoUrl = if (user.pictureSizes.size > 2) {
            user.pictureSizes[2].link
        } else {
            ColorDrawable(ContextCompat.getColor(LocalContext.current, R.color.darkLightGray))
        }
        AsyncImage(
            model = userPhotoUrl,
            contentDescription = stringResource(R.string.image_description_user_image),
            modifier = Modifier
                .size(45.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
        ) {
            Text(
                text = user.name.ifEmpty { stringResource(R.string.user_name_placeholder) },
                style = MaterialTheme.typography.h3,
                modifier = Modifier.wrapContentWidth(),
                color = colorResource(android.R.color.black),
                fontSize = 18.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            val videoCountAndFollowers = TextUtil.formatVideoCountAndFollowers(
                user.videosTotal, user.followersTotal
            )
            Text(
                text = videoCountAndFollowers,
                fontSize = 14.sp,
                modifier = Modifier.wrapContentWidth()
            )
        }
    }
}

private fun getAccompanistWebViewClient(videoUrl: String) =
    object : AccompanistWebViewClient() {
        override fun shouldOverrideUrlLoading(
            webView: WebView, request: WebResourceRequest
        ): Boolean {
            if (request.url.toString() == videoUrl) {
                webView.loadUrl(request.url.toString())
                return true
            }
            return false
        }
    }

private fun getVideoWebViewHeight(context: Context): Dp {
    val screenDimensions = DisplayMetricsUtil.screenDimensions
    // Dynamically set the height of the video container
    val videoHeightPx = if (screenDimensions.width < screenDimensions.height) {
        // We're in portrait mode since screen width < height
        (screenDimensions.width / DisplayMetricsUtil.VIDEO_ASPECT_RATIO).toInt()
    } else {
        // We're in landscape mode
        screenDimensions.height - DisplayMetricsUtil.statusBarHeight
    }
    return context.convertPixelsToDp(videoHeightPx.toFloat())
}

fun Context.convertPixelsToDp(px: Float): Dp {
    return Dp(px / (resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT))
}
