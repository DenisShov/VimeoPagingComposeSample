package com.dshovhenia.compose.playgroundapp.ui.details.comments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.dshovhenia.compose.playgroundapp.R
import com.dshovhenia.compose.playgroundapp.data.cache.model.comment.RelationsComment
import com.dshovhenia.compose.playgroundapp.databinding.FragmentCommentsBinding
import com.dshovhenia.compose.playgroundapp.databinding.ItemCommentBinding
import com.dshovhenia.compose.playgroundapp.paging.VimeoPagedListAdapter
import com.dshovhenia.compose.playgroundapp.paging.base.ListItemViewHolder
import com.dshovhenia.compose.playgroundapp.paging.base.MarginDividerItemDecoration
import com.dshovhenia.compose.playgroundapp.paging.loadState.MyLoadStateAdapter
import com.dshovhenia.compose.playgroundapp.ui.base.BaseFragment
import com.dshovhenia.compose.playgroundapp.util.DisplayMetricsUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommentsFragment : BaseFragment<FragmentCommentsBinding>() {

    private lateinit var pagerAdapter: VimeoPagedListAdapter<RelationsComment>

    private val vm by viewModels<CommentsViewModel>()

    override fun bindView(layoutInflater: LayoutInflater) =
        FragmentCommentsBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val commentUrl = arguments?.getString(ARG_VIMEO_CONNECTION)!!
        vm.onInitialListRequested(commentUrl)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        pagerAdapter = VimeoPagedListAdapter(this,
            object : ListItemViewHolder.ListItemViewHolderGenerator<RelationsComment> {
                override fun generateViewHolder(
                    baseFragment: Fragment, inflater: LayoutInflater, parent: ViewGroup
                ): ListItemViewHolder<RelationsComment> {
                    val layoutInflater = LayoutInflater.from(parent.context)
                    val itemCommentBinding =
                        ItemCommentBinding.inflate(layoutInflater, parent, false)
                    return CommentViewHolder(baseFragment, itemCommentBinding)
                }
            },
            object : DiffUtil.ItemCallback<RelationsComment>() {
                override fun areItemsTheSame(
                    oldItem: RelationsComment, newItem: RelationsComment
                ): Boolean {
                    return oldItem.comment.id == newItem.comment.id
                }

                override fun areContentsTheSame(
                    oldItem: RelationsComment, newItem: RelationsComment
                ): Boolean {
                    return oldItem.comment.uri == newItem.comment.uri && oldItem.comment.text == newItem.comment.text
                }
            })
        pagerAdapter.addLoadStateListener { loadState ->
            when (loadState.refresh) {
                is LoadState.Loading -> {
                    showMessageLayout(false)
                    showProgress()
                }

                is LoadState.NotLoading -> {
                    hideProgress()
                }

                is LoadState.Error -> {
                    hideProgress()
                    showError()
                }
            }
        }

        binding.recyclerview.apply {
            itemAnimator = DefaultItemAnimator()
            layoutManager = setUpLayoutManager(context, DisplayMetricsUtil.isTabletLayout)
            addItemDecoration(
                MarginDividerItemDecoration(
                    context, LinearLayoutManager.VERTICAL, R.dimen.recycler_view_divider_margin
                )
            )
            adapter = pagerAdapter.withLoadStateFooter(MyLoadStateAdapter(pagerAdapter::retry))
        }

        binding.messageLayout.tryAgainButton.setOnClickListener {
            pagerAdapter.retry()
        }

        vm.commentResult.observe(viewLifecycleOwner) {
            pagerAdapter.submitData(lifecycle, it)
        }
    }

    private fun setUpLayoutManager(context: Context?, isTabletLayout: Boolean): LayoutManager? {
        return if (!isTabletLayout) {
            LinearLayoutManager(context)
        } else {
            GridLayoutManager(context, DisplayMetricsUtil.TAB_LAYOUT_SPAN_SIZE)
        }
    }

    fun showProgress() {
        if (pagerAdapter.isEmpty) {
            binding.progressLayout.progressBar.visibility = View.VISIBLE
        }
    }

    fun hideProgress() {
        binding.progressLayout.progressBar.visibility = View.GONE
    }

    fun showError() {
        binding.messageLayout.apply {
            icon.setImageResource(R.drawable.ic_error_outline_black_48dp)
            message.text = getString(R.string.error_generic_server_error)
            tryAgainButton.text = getString(R.string.retry)
        }
        showMessageLayout(true)
    }

    fun showMessageLayout(show: Boolean) {
        binding.messageLayout.messageRoot.visibility = if (show) View.VISIBLE else View.GONE
    }

    companion object {
        private const val ARG_VIMEO_CONNECTION = "vimeo_connection"

        fun newInstance(commentUrl: String): CommentsFragment {
            val args = Bundle()
            args.putString(ARG_VIMEO_CONNECTION, commentUrl)

            val fragment = CommentsFragment()
            fragment.arguments = args
            return fragment
        }
    }

}
