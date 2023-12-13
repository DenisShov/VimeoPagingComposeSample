package com.dshovhenia.compose.playgroundapp.feature.home

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.dshovhenia.compose.playgroundapp.R
import com.dshovhenia.compose.playgroundapp.data.local.db.model.video.RelationsVideo
import com.dshovhenia.compose.playgroundapp.data.mapper.relationsMapper.toCachedVideo
import com.dshovhenia.compose.playgroundapp.feature.base.HandleFailure
import com.dshovhenia.compose.playgroundapp.feature.home.components.SearchBar
import com.dshovhenia.compose.playgroundapp.feature.home.components.VideoItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel) {
    HandleFailure(viewModel.failure)

    val context = LocalContext.current
    val videos = viewModel.videos.collectAsLazyPagingItems()

    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()
    val pullRefreshState = rememberPullRefreshState(isRefreshing, { videos.refresh() })

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    HomeScreenView(
        context,
        navController,
        videos,
        scaffoldState,
        pullRefreshState,
        scope,
        isRefreshing,
        searchByKeyword = { keyword ->
            viewModel.searchKeywordVideos(keyword)
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreenView(
    context: Context,
    navController: NavController,
    videos: LazyPagingItems<RelationsVideo>,
    scaffoldState: ScaffoldState,
    pullRefreshState: PullRefreshState,
    scope: CoroutineScope,
    isRefreshing: Boolean,
    searchByKeyword: (String) -> Unit
) {
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SearchBar(searchByKeyword)
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .pullRefresh(pullRefreshState)
                    .padding(padding)
            ) {
                if (videos.loadState.refresh is LoadState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(vertical = 6.dp)
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(count = videos.itemCount) { index ->
                            videos[index]?.let {
                                VideoItem(
                                    video = it.toCachedVideo(),
                                    onVideoClick = { clickedVideo ->
                                        navController.navigate(
                                            Screen.VideoScreen.withArgs(
                                                clickedVideo.id,
                                                clickedVideo.commentsUri
                                            )
                                        )
                                    })
                            }
                        }

                        when {
                            videos.loadState.append is LoadState.Loading -> {
                                item {
                                    CircularProgressIndicator(
                                        modifier = Modifier.padding(vertical = 6.dp)
                                    )
                                }
                            }

                            videos.loadState.append is LoadState.Error -> {
                                item {
                                    ErrorListItem(
                                        error = (videos.loadState.append as LoadState.Error).error.message,
                                        onTryClicked = {
                                            videos.retry()
                                        }
                                    )
                                }
                            }

                            videos.loadState.refresh is LoadState.Error -> {
                                showSnackBar(
                                    scope = scope,
                                    snackbarHostState = scaffoldState.snackbarHostState,
                                    message = (videos.loadState.refresh as LoadState.Error).error.message
                                        ?: context.resources.getString(R.string.some_error_happened),
                                    actionLabel = context.resources.getString(R.string.try_again),
                                    actionPerformed = { videos.refresh() },
                                    dismissed = {}
                                )
                            }
                        }
                    }
                    PullRefreshIndicator(
                        refreshing = isRefreshing,
                        state = pullRefreshState,
                        modifier = Modifier.align(Alignment.TopCenter)
                    )
                }
            }
        }
    )
}

@Composable
fun ErrorListItem(error: String?, onTryClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.wrapContentHeight(),
            text = error ?: stringResource(id = R.string.some_error_happened),
            color = MaterialTheme.colors.error,
            style = MaterialTheme.typography.body1
        )
        Button(
            modifier = Modifier
                .wrapContentWidth(),
            onClick = onTryClicked
        ) {
            Text(
                text = stringResource(id = R.string.retry),
                color = Color.White,
                style = MaterialTheme.typography.button
            )
        }
    }
}

fun showSnackBar(
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    message: String,
    actionLabel: String,
    actionPerformed: () -> Unit,
    dismissed: () -> Unit
) {
    scope.launch {
        val snackBarResult = snackbarHostState.showSnackbar(
            message = message,
            actionLabel = actionLabel,
            duration = SnackbarDuration.Long
        )
        when (snackBarResult) {
            SnackbarResult.ActionPerformed -> actionPerformed.invoke()
            SnackbarResult.Dismissed -> dismissed.invoke()
        }
    }
}
