package com.epmedu.animeal.extensions

import android.content.Context
import com.epmedu.animeal.resources.R

fun Context.formatMetersToKilometers(distance: Long): String {
    val km = distance / 1000
    val meters = distance % 1000

    return "${if (km > 0) "$km ${getString(R.string.kilometers)} " else ""}${meters}${getString(R.string.meters)}"
}