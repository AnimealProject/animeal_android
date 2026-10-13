package com.epmedu.animeal.home.presentation.ui.map

import com.mapbox.geojson.Feature
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.animation.easeTo
import kotlin.math.ceil

fun MapView.zoomToCluster(sourceIdentifier: String, cluster: Feature) {
    mapboxMap.getGeoJsonClusterLeaves(
        sourceIdentifier,
        cluster,
        Long.MAX_VALUE,
        0
    ) { expectedLeaves ->
        expectedLeaves.value?.let { leaves ->
            leaves.featureCollection?.let { items ->
                val centerPoint = getCenterPoint(items.map { it.geometry() as Point })
                zoomByClusterToPoint(sourceIdentifier, cluster, centerPoint)
            }
        }
    }
}

fun MapView.zoomByClusterToPoint(sourceIdentifier: String, cluster: Feature, point: Point) {
    /* to hide empty clusters */
    val zoomCorrectionShift = 1.0
    mapboxMap.getGeoJsonClusterExpansionZoom(
        sourceIdentifier,
        cluster
    ) {
        val contents = it.value?.value?.contents
        if (contents is Number) {
            zoomTo(point, ceil(contents.toDouble()) + zoomCorrectionShift)
        }
    }
}

fun MapView.zoomTo(point: Point, zoom: Double? = null, duration: Long = 500) {
    mapboxMap.easeTo(
        CameraOptions.Builder()
            .center(point)
            .zoom(zoom ?: mapboxMap.cameraState.zoom)
            .build(),
        MapAnimationOptions.mapAnimationOptions { duration(duration) }
    )
}

private fun getCenterPoint(points: List<Point>): Point {
    val centerLng = points.map { it.longitude() }.average()
    val centerLat = points.map { it.latitude() }.average()
    return Point.fromLngLat(centerLng, centerLat)
}