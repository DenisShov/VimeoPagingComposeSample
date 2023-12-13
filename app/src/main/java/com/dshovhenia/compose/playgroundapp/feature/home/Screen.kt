package com.dshovhenia.compose.playgroundapp.feature.home

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

sealed class Screen(val route: String) {
    object HomeScreen : Screen("home_screen")
    object VideoScreen : Screen("video_screen")

    fun withArgs(vararg args: Any?): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                if (arg is String && arg.contains("/")) {
                    val urlArg = URLEncoder.encode(arg, StandardCharsets.UTF_8.toString())
                    append("/${urlArg}")
                } else {
                    append("/${arg}")
                }
            }
        }
    }
}
