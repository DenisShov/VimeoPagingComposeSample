package com.dshovhenia.compose.playgroundapp.ui.details.custom_view

import android.R.attr.textViewStyle
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.animation.AccelerateInterpolator
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView

class ExpandableTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = textViewStyle
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var mIconImageView: ImageView? = null
    private var mAnimationDirection = ANIMATE_FORWARDS
    private var mMinHeight = STARTING_HEIGHT_UNINITIALIZED
    private var mMaxHeight = 0
    private var mListenersInitialized = false
    private var mLayoutCalcsInitialized = false

    init {
        initializeListeners()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (!mLayoutCalcsInitialized && lineCount != 0) {
            onLayoutCalculations()
            // Prevent this if statement from being called again unless the TextView is refreshed
            // (such as in the case where we click on an 'Up Next' video in VideoFragment
            mLayoutCalcsInitialized = true
        }
    }

    fun setImageIcon(imageIcon: ImageView) {
        mIconImageView = imageIcon
    }

    fun reinitialize() {
        if (!mListenersInitialized) {
            initializeListeners()
        }
        mLayoutCalcsInitialized = false
        mIconImageView?.rotation = 0f
        mAnimationDirection = ANIMATE_FORWARDS
    }

    private fun initializeListeners() {
        setTextViewClickListener()
        setGlobalLayoutListener()
        mListenersInitialized = true
    }

    private fun setTextViewClickListener() {
        setOnClickListener(null)
        setOnClickListener { v: View? ->
            if (mIconImageView == null) {
                throw NullPointerException("Error: imageIcon hasn't been set on ExpandableTextView")
            }
            mAnimationDirection = if (mAnimationDirection == ANIMATE_FORWARDS) {
                expandTextView()
                ANIMATE_BACKWARDS
            } else {
                collapseTextView()
                ANIMATE_FORWARDS
            }
        }
    }

    private fun setGlobalLayoutListener() {
        val vto = viewTreeObserver
        vto.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (mMinHeight > 0) {
                    height = mMinHeight
                    val obs = viewTreeObserver
                    obs.removeOnGlobalLayoutListener(this)
                    mListenersInitialized = false
                }
            }
        })
    }

    private fun onLayoutCalculations() {
        val lineHeight = lineHeight
        val extraHeight = (EXTRA_HEIGHT_COEFFICIENT * lineHeight).toInt()
        mMaxHeight = lineCount * lineHeight + extraHeight
        mMinHeight = Math.min(mMaxHeight, lineHeight * COLLAPSED_TEXTVIEW_MAX_LINES + extraHeight)

        // minHeight == maxHeight so this textView won't be expandable (it's <= 2 lines in height)
        if (mMinHeight == mMaxHeight) {
            mIconImageView?.visibility = GONE
            setOnClickListener(null)
        }
    }

    private fun expandTextView() {
        val animatorSet = AnimatorSet()
        val textAnimation = ObjectAnimator.ofInt(this, "height", mMaxHeight)
        textAnimation.setDuration(ANIMATION_DURATION.toLong()).interpolator =
            AccelerateInterpolator()
        val iconAnimation = ObjectAnimator.ofFloat(
            mIconImageView, "rotation", MIN_ROTATION, MAX_ROTATION
        )
        iconAnimation.duration = ANIMATION_DURATION.toLong()
        animatorSet.play(textAnimation).with(iconAnimation)
        animatorSet.start()
    }

    private fun collapseTextView() {
        val animatorSet = AnimatorSet()
        val textAnimation = ObjectAnimator.ofInt(this, "height", mMinHeight)
        textAnimation.setDuration(ANIMATION_DURATION.toLong()).interpolator =
            AccelerateInterpolator()
        val iconAnimation = ObjectAnimator.ofFloat(
            mIconImageView, "rotation", MAX_ROTATION, MIN_ROTATION
        )
        iconAnimation.duration = ANIMATION_DURATION.toLong()
        animatorSet.play(textAnimation).with(iconAnimation)
        animatorSet.start()
    }

    companion object {
        private const val STARTING_HEIGHT_UNINITIALIZED = -1
        private const val COLLAPSED_TEXTVIEW_MAX_LINES = 2
        private const val MIN_ROTATION = 0f
        private const val MAX_ROTATION = 180f
        private const val EXTRA_HEIGHT_COEFFICIENT = 0.15
        private const val ANIMATION_DURATION = 75
        private const val ANIMATE_FORWARDS = true
        private const val ANIMATE_BACKWARDS = false
    }
}