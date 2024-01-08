package com.dshovhenia.vimeo.paging.compose.sample.core.utils

import android.content.res.Resources

object DisplayMetricsUtil {

  const val TAB_LAYOUT_SPAN_SIZE = 2
  const val VIDEO_ASPECT_RATIO = 16.toFloat() / 9
  private const val SCREEN_TABLET_DP_WIDTH = 600

  class Dimensions(val width: Int, val height: Int)

  val screenDimensions: Dimensions
    get() {
      val displayMetrics = Resources.getSystem().displayMetrics
      return Dimensions(displayMetrics.widthPixels, displayMetrics.heightPixels)
    }

  val statusBarHeight: Int
    get() {
      var result = 0
      val resourceId = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android")
      if (resourceId > 0) {
        result = Resources.getSystem().getDimensionPixelSize(resourceId)
      }
      return result
    }

}
