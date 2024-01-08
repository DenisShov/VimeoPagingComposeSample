package com.dshovhenia.vimeo.paging.compose.sample.feature

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dshovhenia.vimeo.paging.compose.sample.feature.details.VideoScreen
import com.dshovhenia.vimeo.paging.compose.sample.feature.home.HomeScreen
import com.dshovhenia.vimeo.paging.compose.sample.feature.home.HomeViewModel
import com.dshovhenia.vimeo.paging.compose.sample.feature.home.Screen

const val ARG_VIDEO_ID = "arg_video_id"
const val ARG_COMMENTS_URL = "arg_comments_url"

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(route = Screen.HomeScreen.route) {
            val viewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(navController, viewModel)
        }
        composable(
            route = Screen.VideoScreen.route + "/{$ARG_VIDEO_ID}/{$ARG_COMMENTS_URL}",
            arguments = listOf(navArgument(ARG_VIDEO_ID) {
                type = NavType.LongType
                nullable = false
            }, navArgument(ARG_COMMENTS_URL) {
                type = NavType.StringType
                nullable = false
            })
        ) {
            VideoScreen()
        }
    }
}
