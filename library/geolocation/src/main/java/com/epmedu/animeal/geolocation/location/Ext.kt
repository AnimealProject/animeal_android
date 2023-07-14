package com.epmedu.animeal.geolocation.location

import com.mapbox.geojson.Point
import com.mapbox.turf.TurfMeasurement

private const val DEFAULT_NEAREST_DISTANCE_KM = 10

fun Point.isNearTo(point: Point, nearDistanceInKm: Int = DEFAULT_NEAREST_DISTANCE_KM): Boolean =
    distanceInKmTo(point) < nearDistanceInKm

fun Point.distanceInKmTo(point: Point): Double =
    TurfMeasurement.distance(this, point)
