package com.epmedu.animeal.home.presentation.ui

import com.epmedu.animeal.home.utils.MarkerCache
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager

data class MarkerController(private val mapView: MapView) {
    val markerCache: MarkerCache = MarkerCache(mapView.context)
    val pointAnnotationManager: PointAnnotationManager =
        mapView.annotations.createPointAnnotationManager()
}
