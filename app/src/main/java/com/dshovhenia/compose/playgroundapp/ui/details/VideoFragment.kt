package com.dshovhenia.compose.playgroundapp.ui.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Constraints
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dshovhenia.compose.playgroundapp.R
import com.dshovhenia.compose.playgroundapp.data.cache.model.video.CachedVideo
import com.dshovhenia.compose.playgroundapp.databinding.FragmentVideoBinding
import com.dshovhenia.compose.playgroundapp.ui.base.BaseFragment
import com.dshovhenia.compose.playgroundapp.ui.details.comments.CommentsPagerAdapter
import com.dshovhenia.compose.playgroundapp.util.DisplayMetricsUtil
import com.dshovhenia.compose.playgroundapp.util.VimeoTextUtil
import com.google.android.material.appbar.CollapsingToolbarLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class VideoFragment : BaseFragment<FragmentVideoBinding>() {

    private lateinit var pagerAdapter: CommentsPagerAdapter
    private lateinit var video: CachedVideo
    private lateinit var screenDimensions: DisplayMetricsUtil.Dimensions
    private var videoId by Delegates.notNull<Long>()

    private val vm by viewModels<VideoViewModel>()

    override fun bindView(layoutInflater: LayoutInflater) =
        FragmentVideoBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        screenDimensions = DisplayMetricsUtil.screenDimensions

        videoId =
            savedInstanceState?.getLong(SAVED_VIDEO_ID) ?: requireArguments().getLong(ARG_VIDEO_ID)
        video = vm.getVideoById(videoId)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(SAVED_VIDEO_ID, videoId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPager()
        initViews()
    }

    private fun initPager() {
        pagerAdapter = CommentsPagerAdapter(
            requireContext(), childFragmentManager, video.commentsUri
        )
        binding.viewpager.apply {
            offscreenPageLimit = 1
            adapter = pagerAdapter
            binding.tabLayout.setupWithViewPager(this)
        }
    }

    private fun initViews() {
        // Dynamically set the height of the video container
        val videoHeight = if (screenDimensions.width < screenDimensions.height) {
            // We're in portrait mode since screen width < height
            (screenDimensions.width / DisplayMetricsUtil.VIDEO_ASPECT_RATIO).toInt()
        } else {
            // We're in landscape mode
            screenDimensions.height - DisplayMetricsUtil.statusBarHeight
        }
        binding.webView.layoutParams =
            Constraints.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, videoHeight)

        // We need to set the top margin of the details Linear Layout to the calculated height of the video container
        val params = binding.detailsLayout.layoutParams as CollapsingToolbarLayout.LayoutParams
        params.setMargins(0, videoHeight, 0, 0)
        binding.detailsLayout.layoutParams = params

        binding.videoTimelengthText.text = VimeoTextUtil.formatSecondsToDuration(video.duration)
        binding.videoTitleText.text = video.name

        video.createdTime?.let {
            binding.playsText.text = VimeoTextUtil.formatVideoAgeAndPlays(video.videoPlays, it)
        }

        VimeoTextUtil.hideOrDisplayTextViewIfNullString(
            binding.includeDescriptionLayout.expandableTextView, video.description
        )
        binding.includeDescriptionLayout.expandableTextView.setImageIcon(
            binding.includeDescriptionLayout.arrowImage
        )
        binding.includeDescriptionLayout.expandableTextView.reinitialize()

        video.user?.pictureSizes?.let {
            Glide.with(this@VideoFragment).load(it[2].link).circleCrop()
                .placeholder(R.drawable.user_image_placeholder)
                .fallback(R.drawable.user_image_placeholder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.includeItemUser.itemUserImageView)
        }

        binding.includeItemUser.itemUserNameText.text = video.user?.name ?: "no name"

        var videoCountAndFollowers = "0"
        if (video.user?.videosTotal != null && video.user?.followersTotal != null) {
            videoCountAndFollowers = VimeoTextUtil.formatVideoCountAndFollowers(
                video.user?.videosTotal!!,
                video.user?.followersTotal!!
            )
        }
        binding.includeItemUser.itemUserFollowersText.text = videoCountAndFollowers

        initWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun initWebView() {
        val webViewPlayerUrl =
            "https://player.vimeo.com/video/" + VimeoTextUtil.generateIdFromUri(video.uri)

        binding.webView.apply {
            settings.javaScriptEnabled = true
            settings.javaScriptCanOpenWindowsAutomatically = true
            settings.mediaPlaybackRequiresUserGesture = false
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(webView: WebView, url: String): Boolean {
                    if (url == webViewPlayerUrl) {
                        webView.loadUrl(url)
                        return true
                    }
                    return false
                }
            }
            loadUrl(webViewPlayerUrl)
        }
    }

    companion object {
        const val ARG_VIDEO_ID = "arg_video_id"
        const val SAVED_VIDEO_ID = "saved_video_id"
    }

}
