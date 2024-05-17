package com.epmedu.animeal.extensions

import android.content.Context
import com.epmedu.animeal.resources.R
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.ceil

private const val YEAR_MONTH_DAY_DASH_FORMATTER_PATTERN = "yyyy-MM-dd"
const val HOUR_IN_MILLIS = 3_600_000L
const val MINUTE_IN_MILLIS = 60_000L
const val HOUR_IN_MINUTES = 60L
const val SECOND_IN_MILLIS = 1_000L

val DEFAULT_LOCALE: Locale
    get() = Locale.getDefault()

val YEAR_MONTH_DAY_DASH_FORMATTER: DateTimeFormatter = DateTimeFormatter
    .ofPattern(YEAR_MONTH_DAY_DASH_FORMATTER_PATTERN)
    .withLocale(DEFAULT_LOCALE)

/**
 * Consider number in milliseconds.
 */
fun Context.formatNumberToHourMin(time: Long?): String? {
    if (time == null || time < 0) return null
    val timeInMinutes = ceil(time.toDouble() / MINUTE_IN_MILLIS).toLong()
    val hours = timeInMinutes / HOUR_IN_MINUTES
    val minutes = timeInMinutes % HOUR_IN_MINUTES
    return "${if (hours > 0) "$hours ${getString(R.string.hours)} " else ""}$minutes ${getString(R.string.minutes)}"
}
