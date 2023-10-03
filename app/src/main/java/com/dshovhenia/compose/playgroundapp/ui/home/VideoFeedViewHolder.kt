package com.dshovhenia.compose.playgroundapp.ui.home

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.dshovhenia.compose.playgroundapp.R
import com.dshovhenia.compose.playgroundapp.data.cache.mapper.relationsMapper.toCachedVideo
import com.dshovhenia.compose.playgroundapp.data.cache.model.video.RelationsVideo
import com.dshovhenia.compose.playgroundapp.databinding.ItemVideoFeedBinding
import com.dshovhenia.compose.playgroundapp.paging.base.ListItemViewHolder
import com.dshovhenia.compose.playgroundapp.util.DisplayMetricsUtil.Dimensions
import com.dshovhenia.compose.playgroundapp.util.VimeoTextUtil

class VideoFeedViewHolder(
    baseFragment: Fragment,
    private val itemVideoFeedBinding: ItemVideoFeedBinding,
    screenDimensions: Dimensions
) : ListItemViewHolder<RelationsVideo>(baseFragment, itemVideoFeedBinding.root) {

    init {
        itemVideoFeedBinding.videoImageView.layoutParams =
            ConstraintLayout.LayoutParams(screenDimensions.width, screenDimensions.height)
    }

    override fun bind(listItem: RelationsVideo, onItemClick: ((RelationsVideo) -> Unit)?) {
        val video = listItem.toCachedVideo()

        itemView.setOnClickListener { onItemClick?.invoke(listItem) }

        itemVideoFeedBinding.apply {
            videoTitleTextView.text = video.name
            userTextView.text = video.user?.name ?: "no name"

            val totalLikes = video.likesTotal
            likesTextView.text = itemView.resources.getQuantityString(
                R.plurals.video_feed_likes_plural, totalLikes, totalLikes
            )
            timeLengthTextView.text =
                VimeoTextUtil.formatSecondsToDuration(video.duration)

            if (video.pictureSizes.size > 2) {
                Glide.with(mBaseFragment)
                    .load(video.pictureSizes[2].link)
                    .placeholder(R.drawable.video_image_placeholder)
                    .fallback(R.drawable.video_image_placeholder)
                    .fitCenter()
                    .into(videoImageView)
            }
        }
    }
}
