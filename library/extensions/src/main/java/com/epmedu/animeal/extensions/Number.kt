package com.epmedu.animeal.extensions

fun Long.formatMetersToKilometers(): String {
    val km = this / 1000
    val meters = this % 1000

    return "${if (km > 0) "$km km " else ""}${meters}m"
}