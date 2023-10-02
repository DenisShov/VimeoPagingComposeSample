package com.dshovhenia.compose.playgroundapp.ui.details.comments

import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.dshovhenia.compose.playgroundapp.R
import com.dshovhenia.compose.playgroundapp.data.cache.model.comment.CachedComment
import com.dshovhenia.compose.playgroundapp.data.cache.model.comment.RelationsComment
import com.dshovhenia.compose.playgroundapp.databinding.ItemCommentBinding
import com.dshovhenia.compose.playgroundapp.paging.base.ListItemViewHolder
import com.dshovhenia.compose.playgroundapp.util.VimeoTextUtil
import java.util.Locale

class CommentViewHolder(
    baseFragment: Fragment,
    private val itemCommentBinding: ItemCommentBinding
) : ListItemViewHolder<RelationsComment>(baseFragment, itemCommentBinding.root) {

    override fun bind(listItem: RelationsComment, onItemClick: ((RelationsComment) -> Unit)?) {
        val comment = CachedComment().initialize(listItem)

        itemCommentBinding.apply {
            commentTextView.text = comment.text

            comment.created_on?.let {
                val commentAge = String.format(
                    Locale.getDefault(),
                    " ⋅ %s",
                    VimeoTextUtil.dateCreatedToTimePassed(it)
                )
                ageTextView.text = commentAge
            }

            comment.user?.let {
                userNameTextView.text = it.name

                val pictureUrl = it.pictureSizes[1].link
                Glide.with(mBaseFragment).load(pictureUrl)
                    .placeholder(R.drawable.user_image_placeholder)
                    .fallback(R.drawable.user_image_placeholder)
                    .circleCrop().into(userImageView)
            }
        }
    }
}
