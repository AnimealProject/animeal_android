package com.epmedu.animeal.common.data.model

data class MapLocation(val latitude: Double = 0.0, val longitude: Double = 0.0) {
    val isInitial = latitude == 0.0 && longitude == 0.0
}