package com.epmedu.animeal.extensions

fun formatMetersToKilometers(total: Long): String {
    val km = total / 1000
    val meters = total % 1000

    return "${if (km > 0) "$km km " else ""}${meters}m"
}