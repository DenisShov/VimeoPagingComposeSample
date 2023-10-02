package com.dshovhenia.compose.playgroundapp.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dshovhenia.compose.playgroundapp.R
import com.dshovhenia.compose.playgroundapp.data.cache.mapper.relationsMapper.RelationsVideoMapper
import com.dshovhenia.compose.playgroundapp.data.cache.model.video.RelationsVideo
import com.dshovhenia.compose.playgroundapp.databinding.FragmentHomeBinding
import com.dshovhenia.compose.playgroundapp.databinding.ItemVideoFeedBinding
import com.dshovhenia.compose.playgroundapp.paging.VimeoPagedListAdapter
import com.dshovhenia.compose.playgroundapp.paging.base.ListItemViewHolder
import com.dshovhenia.compose.playgroundapp.paging.base.MarginDividerItemDecoration
import com.dshovhenia.compose.playgroundapp.paging.loadState.MyLoadStateAdapter
import com.dshovhenia.compose.playgroundapp.ui.base.BaseFragment
import com.dshovhenia.compose.playgroundapp.ui.details.VideoFragment
import com.dshovhenia.compose.playgroundapp.util.DisplayMetricsUtil
import com.dshovhenia.compose.playgroundapp.util.KeyboardUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

  @Inject
  lateinit var relationsVideoMapper: RelationsVideoMapper

  private lateinit var screenDimensions: DisplayMetricsUtil.Dimensions
  private lateinit var pagerAdapter: VimeoPagedListAdapter<RelationsVideo>
  private lateinit var concatAdapter: ConcatAdapter

  private val vm by viewModels<HomeViewModel>()

  override fun bindView(layoutInflater: LayoutInflater) =
    FragmentHomeBinding.inflate(layoutInflater)

  override fun onAttach(context: Context) {
    super.onAttach(context)
    screenDimensions = DisplayMetricsUtil.screenDimensions
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    initAdapter()
    if (savedInstanceState == null) {
      vm.showStaffPickVideos()
    }
  }

  private fun initAdapter() {
    pagerAdapter = VimeoPagedListAdapter(
      this,
      object : ListItemViewHolder.ListItemViewHolderGenerator<RelationsVideo> {
        override fun generateViewHolder(
          baseFragment: Fragment, inflater: LayoutInflater, parent: ViewGroup
        ): ListItemViewHolder<RelationsVideo> {

          val layoutInflater = LayoutInflater.from(parent.context)
          val itemVideoFeedBinding = ItemVideoFeedBinding.inflate(layoutInflater, parent, false)
          return VideoFeedViewHolder(
            baseFragment,
            itemVideoFeedBinding,
            relationsVideoMapper,
            DisplayMetricsUtil.getDimensToFillWidth(
              DisplayMetricsUtil.screenDimensions,
              DisplayMetricsUtil.isTabletLayout
            )
          )
        }
      },
      object : DiffUtil.ItemCallback<RelationsVideo>() {
        override fun areItemsTheSame(oldItem: RelationsVideo, newItem: RelationsVideo): Boolean {
          return oldItem.video.id == newItem.video.id
        }

        override fun areContentsTheSame(oldItem: RelationsVideo, newItem: RelationsVideo): Boolean {
          return oldItem.video.uri == newItem.video.uri &&
                  oldItem.video.name == newItem.video.name &&
                  oldItem.video.description == newItem.video.description
        }
      }
    )
    pagerAdapter.onItemClick = { relationsVideo ->
      val bundle = bundleOf(VideoFragment.ARG_VIDEO_ID to relationsVideo.video.id)
      findNavController().navigate(R.id.action_homeFragment_to_videoFragment, bundle)
    }
    concatAdapter = pagerAdapter.withLoadStateFooter(MyLoadStateAdapter(pagerAdapter::retry))
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initSearch()
    initViews()
  }

  private fun initSearch() {
    binding.searchEditText.setOnEditorActionListener(OnEditorActionListener { v, actionId, _ ->
      if (actionId == EditorInfo.IME_ACTION_SEARCH) {
        activity?.also { KeyboardUtil.hideKeyboard(it) }

        if (v.text.toString().isEmpty()) {
          vm.clearVideos()
          vm.showStaffPickVideos()
          vm.searchQuery = null
        } else {
          vm.searchVideos(v.text.toString())
        }

        return@OnEditorActionListener true
      }
      false
    })
  }

  private fun initViews() {
    binding.messageLayout.tryAgainButton.setOnClickListener { pagerAdapter.retry() }

    pagerAdapter.addLoadStateListener { loadState ->
      when (loadState.refresh) {
        is LoadState.Loading -> {
          if (pagerAdapter.isEmpty) {
            showProgressCircle()
          }
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
      setBackgroundColor(resources.getColor(R.color.mediumLightGray, null))
      itemAnimator = DefaultItemAnimator()
      layoutManager = setUpLayoutManager(context, DisplayMetricsUtil.isTabletLayout)
      addItemDecoration(
        MarginDividerItemDecoration(
          context,
          LinearLayoutManager.VERTICAL,
          R.dimen.recycler_view_divider_margin
        )
      )
      adapter = concatAdapter
    }

    binding.swipeToRefresh.apply {
      setProgressBackgroundColorSchemeResource(R.color.colorPrimaryDark)
      setColorSchemeResources(R.color.white)
      setOnRefreshListener {
        pagerAdapter.refresh()
      }
    }

    vm.videoResult.observe(viewLifecycleOwner, Observer<PagingData<RelationsVideo>> {
      pagerAdapter.submitData(lifecycle, it)
    })
  }

  private fun setUpLayoutManager(context: Context?, isTabletLayout: Boolean) =
    if (!isTabletLayout) {
      LinearLayoutManager(context)
    } else {
      GridLayoutManager(context, DisplayMetricsUtil.TAB_LAYOUT_SPAN_SIZE)
    }

  fun hideProgress() {
    showMessageLayout(false)
    binding.swipeToRefresh.isRefreshing = false
    binding.progressLayout.progressBar.visibility = View.GONE
  }

  fun showProgressCircle() {
    showMessageLayout(false)
    binding.swipeToRefresh.isRefreshing = false
    binding.progressLayout.progressBar.visibility = View.VISIBLE
  }

  private fun showError() {
    binding.messageLayout.apply {
      icon.setImageResource(R.drawable.ic_error_outline_black_48dp)
      message.text = getString(R.string.error_generic_server_error)
      tryAgainButton.text = getString(R.string.retry)
    }
    showMessageLayout(true)
  }

  private fun showMessageLayout(show: Boolean) {
    binding.messageLayout.messageRoot.visibility = if (show) View.VISIBLE else View.GONE
  }

}
