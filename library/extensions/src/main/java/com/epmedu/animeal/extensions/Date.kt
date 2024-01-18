package com.epmedu.animeal.extensions

import android.content.Context
import com.epmedu.animeal.resources.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.ceil

private const val DAY_MONTH_NAME_COMMA_YEAR_FORMATTER_PATTERN = "d MMM, yyyy"
private const val DAY_MONTH_YEAR_DOT_FORMATTER_PATTERN = "dd.MM.yyyy"
private const val MONTH_DAY_YEAR_SLASH_FORMATTER_PATTERN = "MM/dd/yyyy"
private const val YEAR_MONTH_DAY_DASH_FORMATTER_PATTERN = "yyyy-MM-dd"
private const val DAY_MONTH_YEAR_SLASH_FORMATTER_PATTERN = "dd/MM/yyyy"
const val HOUR_IN_MILLIS = 3_600_000L
const val MINUTE_IN_MILLIS = 60_000L
const val HOUR_IN_MINUTES = 60L
const val SECOND_IN_MILLIS = 1_000L

val DEFAULT_LOCALE: Locale
    get() = Locale.getDefault()

val DAY_MONTH_NAME_COMMA_YEAR_FORMATTER: DateTimeFormatter =
    DateTimeFormatter
        .ofPattern(DAY_MONTH_NAME_COMMA_YEAR_FORMATTER_PATTERN)
        .withLocale(DEFAULT_LOCALE)

val DAY_MONTH_YEAR_DOT_FORMATTER = DateTimeFormatter
    .ofPattern(DAY_MONTH_YEAR_DOT_FORMATTER_PATTERN)
    .withLocale(DEFAULT_LOCALE)

val DAY_MONTH_YEAR_SLASH_FORMATTER = DateTimeFormatter
    .ofPattern(DAY_MONTH_YEAR_SLASH_FORMATTER_PATTERN)
    .withLocale(DEFAULT_LOCALE)

val MONTH_DAY_YEAR_SLASH_FORMATTER = DateTimeFormatter
    .ofPattern(MONTH_DAY_YEAR_SLASH_FORMATTER_PATTERN)
    .withLocale(DEFAULT_LOCALE)

val YEAR_MONTH_DAY_DASH_FORMATTER = DateTimeFormatter
    .ofPattern(YEAR_MONTH_DAY_DASH_FORMATTER_PATTERN)
    .withLocale(DEFAULT_LOCALE)

val DEFAULT_DATE_FORMATTER: DateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
    .withLocale(DEFAULT_LOCALE)

fun formatDateToString(
    date: LocalDate?,
    formatter: DateTimeFormatter = DAY_MONTH_NAME_COMMA_YEAR_FORMATTER
): String {
    date ?: return ""
    return date.format(formatter)
}

fun reformatDateToString(
    dateString: String,
    originalFormatter: DateTimeFormatter,
    newFormatter: DateTimeFormatter
): String {
    val date = LocalDate.parse(dateString, originalFormatter)
    date ?: return ""
    return date.format(newFormatter)
}

fun tryParseDate(rawDate: String?): LocalDate? {
    if (rawDate.isNullOrEmpty()) {
        return null
    }
    return runCatching {
        LocalDate.parse(rawDate, DAY_MONTH_NAME_COMMA_YEAR_FORMATTER)
    }.getOrNull()
}

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
