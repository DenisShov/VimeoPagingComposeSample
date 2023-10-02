package com.dshovhenia.compose.playgroundapp.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import com.dshovhenia.compose.playgroundapp.paging.base.ListItemViewHolder
import com.dshovhenia.compose.playgroundapp.paging.base.ListItemViewHolder.ListItemViewHolderGenerator

class VimeoPagedListAdapter<T : Any>(
  private val mBaseFragment: Fragment,
  private val mViewHolderGenerator: ListItemViewHolderGenerator<T>,
  diffCallback: ItemCallback<T>
) : PagingDataAdapter<T, ListItemViewHolder<T>>(diffCallback) {

  var onItemClick: ((T) -> Unit)? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder<T> {
    val layoutInflater = LayoutInflater.from(parent.context)
    return mViewHolderGenerator.generateViewHolder(mBaseFragment, layoutInflater, parent)
  }

  override fun onBindViewHolder(holder: ListItemViewHolder<T>, position: Int) {
    val item = getItem(position)
    if (item != null) {
      holder.bind(item, onItemClick)
    }
  }

  val isEmpty: Boolean
    get() = itemCount == 0

}
