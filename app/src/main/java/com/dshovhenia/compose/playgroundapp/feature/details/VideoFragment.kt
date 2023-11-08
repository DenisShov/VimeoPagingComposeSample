package com.dshovhenia.compose.playgroundapp.feature.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class VideoFragment : Fragment() {

    private lateinit var composeView: ComposeView

    @Inject
    lateinit var viewModelAssistedFactory: VideoViewModel.Factory
    private val videoId by lazy { arguments?.getLong(ARG_VIDEO_ID)!! }
    private val commentsUrl by lazy { arguments?.getString(ARG_COMMENTS_URL)!! }

    private val viewModel: VideoViewModel by viewModels {
        VideoViewModel.provideFactory(viewModelAssistedFactory, videoId, commentsUrl)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        composeView.setContent {
            VideoScreen(viewModel)
        }
    }

    companion object {
        const val ARG_VIDEO_ID = "arg_video_id"
        const val ARG_COMMENTS_URL = "arg_comments_url"
    }
}
