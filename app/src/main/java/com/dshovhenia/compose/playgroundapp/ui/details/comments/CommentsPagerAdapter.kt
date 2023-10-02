package com.dshovhenia.compose.playgroundapp.ui.details.comments

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dshovhenia.compose.playgroundapp.R

class CommentsPagerAdapter(
  private val mContext: Context, fm: FragmentManager, private var commentUrl: String
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

  override fun getItem(position: Int): Fragment {
    return CommentsFragment.newInstance(commentUrl)
  }

  override fun getCount(): Int {
    return NUMBER_TABS
  }

  override fun getPageTitle(position: Int): CharSequence {
    return mContext.resources.getString(R.string.comments)
  }

  companion object {
    private const val NUMBER_TABS = 1
  }
}
