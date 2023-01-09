package com.epmedu.animeal.extensions

import android.content.Context
import com.epmedu.animeal.resources.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

private const val DAY_MONTH_COMMA_YEAR_FORMATTER_PATTERN = "d MMM, yyyy"
private const val DAY_MONTH_YEAR_DOT_FORMATTER_PATTERN = "dd.MM.yyyy"
const val HOUR_IN_MILLIS = 3_600_000L
const val MINUTE_IN_MILLIS = 60_000L

val DEFAULT_LOCALE: Locale
    get() = Locale.getDefault()

val DAY_MONTH_COMMA_YEAR_FORMATTER: DateTimeFormatter =
    DateTimeFormatter
        .ofPattern(DAY_MONTH_COMMA_YEAR_FORMATTER_PATTERN)
        .withLocale(DEFAULT_LOCALE)

val DAY_MONTH_YEAR_DOT_FORMATTER = DateTimeFormatter
    .ofPattern(DAY_MONTH_YEAR_DOT_FORMATTER_PATTERN)
    .withLocale(DEFAULT_LOCALE)

val DEFAULT_DATE_FORMATTER: DateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
    .withLocale(DEFAULT_LOCALE)

fun formatDateToString(date: LocalDate?, formatter: DateTimeFormatter): String {
    date ?: return ""
    return date.format(formatter)
}

fun tryParseDate(rawDate: String?): LocalDate? {
    if (rawDate.isNullOrEmpty()) {
        return null
    }
    return runCatching {
        LocalDate.parse(rawDate, DAY_MONTH_COMMA_YEAR_FORMATTER)
    }.getOrNull()
}

/**
 * Consider number in milliseconds
 */
fun Context.formatNumberToHourMin(time: Long?): String? {
    if (time == null || time < 0) return null
    val hours = time / HOUR_IN_MILLIS
    val minutes = time % HOUR_IN_MILLIS / MINUTE_IN_MILLIS

    return "${if (hours > 0) "$hours ${getString(R.string.hours)} " else ""}$minutes ${getString(R.string.minutes)}"
}
