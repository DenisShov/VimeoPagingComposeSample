package com.dshovhenia.compose.playgroundapp.core.utils

import android.view.View
import android.widget.TextView
import java.util.Date
import java.util.Locale

object TextUtil {

    private const val THOUSAND = 1000
    private const val MILLION = 1000000
    private const val SIXTY = 60
    private const val DAYS_IN_YEAR = 365
    private const val THIRTY = 30
    private const val HOURS_IN_DAY = 24

    fun generateIdFromUri(uri: String): Int {
        val paths = uri.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return Integer.valueOf(paths[paths.size - 1])
    }

    fun formatSecondsToDuration(seconds: Int) =
        String.format(Locale.getDefault(), "%02d:%02d", seconds / SIXTY, seconds % SIXTY)

    fun dateCreatedToTimePassed(dateCreated: Date) =
        minutesToTimePassed(getDifferenceMinutes(dateCreated, Date()))

    fun formatVideoAgeAndPlays(plays: Long?, dateCreated: Date?) =
        if (plays != null && dateCreated != null) {
            String.format(
                Locale.getDefault(), "%s ⋅ %s",
                formatIntMetric(plays, "play", "plays"),
                dateCreatedToTimePassed(dateCreated)
            )
        } else if (plays == null && dateCreated != null) {
            dateCreatedToTimePassed(dateCreated)
        } else if (plays != null) {
            formatIntMetric(plays, "play", "plays")
        } else ""

    fun formatVideoCountAndFollowers(videos: Int, followers: Int) =
        String.format(
            Locale.getDefault(), "%s ⋅ %s",
            formatIntMetric(videos.toLong(), "video", "videos"),
            formatIntMetric(followers.toLong(), "follower", "followers")
        )

    fun hideOrDisplayTextViewIfNullString(textView: TextView, text: String?) {
        textView.text = text
        textView.visibility = if (text.isNullOrEmpty()) View.GONE else View.VISIBLE
    }

    private fun minutesToTimePassed(minutes: Int) =
        if (minutes < SIXTY) {
            String.format(
                Locale.getDefault(),
                "%d minute" + (if (minutes != 1) "s" else "") + " ago",
                minutes
            )
        } else {
            hoursToTimePassed(minutes / SIXTY)
        }

    private fun hoursToTimePassed(hours: Int) =
        if (hours < HOURS_IN_DAY) {
            String.format(
                Locale.getDefault(),
                "%d hour" + (if (hours != 1) "s" else "") + " ago",
                hours
            )
        } else {
            daysToTimePassed(hours / HOURS_IN_DAY)
        }

    private fun daysToTimePassed(days: Int) =
        when {
            days < THIRTY -> {
                String.format(
                    Locale.getDefault(),
                    "%d day" + (if (days != 1) "s" else "") + " ago",
                    days
                )
            }

            days < DAYS_IN_YEAR -> {
                val months = days / THIRTY
                String.format(
                    Locale.getDefault(),
                    "%d month" + (if (months != 1) "s" else "") + " ago",
                    months
                )
            }

            else -> {
                val years = days / DAYS_IN_YEAR
                String.format(
                    Locale.getDefault(),
                    "%d year" + (if (years != 1) "s" else "") + " ago",
                    years
                )
            }
        }

    private fun getDifferenceMinutes(d1: Date, d2: Date) =
        ((d2.time - d1.time) / (SIXTY * THOUSAND) + 1).toInt()

    private fun formatIntMetric(value: Long, singularMetric: String, pluralMetric: String) =
        when {
            value < THOUSAND -> {
                String.format(
                    Locale.getDefault(), "%d %s", value,
                    if (value != 1L) pluralMetric else singularMetric
                )
            }

            value < MILLION -> {
                String.format(
                    Locale.getDefault(),
                    "%.1fk %s",
                    value.toDouble() / THOUSAND,
                    pluralMetric
                )
            }

            else -> {
                String.format(
                    Locale.getDefault(),
                    "%.1fm %s",
                    value.toDouble() / MILLION,
                    pluralMetric
                )
            }
        }
}
