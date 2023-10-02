package com.dshovhenia.compose.playgroundapp.paging.loadState

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.dshovhenia.compose.playgroundapp.paging.loadState.LoadStateViewHolder

class MyLoadStateAdapter(
  private val retry: () -> Unit
) : LoadStateAdapter<LoadStateViewHolder>() {

  override fun onCreateViewHolder(
    parent: ViewGroup,
    loadState: LoadState
  ) = LoadStateViewHolder(parent, retry)

  override fun onBindViewHolder(
      holder: LoadStateViewHolder,
      loadState: LoadState
  ) = holder.bind(loadState)
}
