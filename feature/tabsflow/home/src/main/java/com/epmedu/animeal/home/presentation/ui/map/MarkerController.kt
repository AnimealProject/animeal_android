package com.epmedu.animeal.home.presentation.ui.map

import androidx.compose.ui.graphics.toArgb
import com.epmedu.animeal.extensions.consume
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.foundation.theme.CustomColor
import com.epmedu.animeal.home.utils.MarkerCache
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createCircleAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager

class MarkerController(
    private val onFeedingPointClick: (point: FeedingPointModel) -> Unit,
    mapView: MapView
) {
    private val markerCache = MarkerCache(mapView.context)
    private val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
    private val selectedPointAnnotationManager = mapView.annotations.createCircleAnnotationManager()

    private val currentFeedingPoints = mutableListOf<FeedingPointModel>()

    private val onPointClickListener: (PointAnnotation) -> Boolean = { pointAnnotation ->
        consume {
            currentFeedingPoints.find { pointAnnotation.point == it.coordinates }?.let {
                onFeedingPointClick(it)
            }
        }
    }

    fun drawMarkers(feedingPoints: List<FeedingPointModel>) {
        resetMarkerState()
        initMarkerState(feedingPoints)
    }

    private fun resetMarkerState() {
        pointAnnotationManager.deleteAll()
        pointAnnotationManager.removeClickListener(onPointClickListener)
        currentFeedingPoints.clear()
    }

    private fun initMarkerState(feedingPoints: List<FeedingPointModel>) {
        if (feedingPoints.size <= 1) return
        val pointAnnotationOptionsList = mutableListOf<PointAnnotationOptions>()
        currentFeedingPoints.addAll(feedingPoints)

        currentFeedingPoints.onEach { feedingPoint ->
            val icon = markerCache.getMarker(feedingPoint.getDrawableRes())

            val pointAnnotationOptions = PointAnnotationOptions()
                .withPoint(feedingPoint.coordinates)
                .withIconImage(icon)

            pointAnnotationOptionsList.add(pointAnnotationOptions)
        }
        pointAnnotationManager.create(pointAnnotationOptionsList)

        pointAnnotationManager.addClickListener(onPointClickListener)
    }

    fun drawSelectedMarkerBackground(feedingPoint: FeedingPointModel?) {
        val circleAnnotationOptionsList = mutableListOf<CircleAnnotationOptions>()

        selectedPointAnnotationManager.deleteAll()
        if (feedingPoint != null) {
            val circleAnnotationOptions = CircleAnnotationOptions()

            circleAnnotationOptions
                .withPoint(feedingPoint.coordinates)
                .withCircleRadius(74.0)
                .withCircleColor(CustomColor.CarminePink.toArgb())
                .withCircleOpacity(0.2)
                .withCircleStrokeColor(CustomColor.CarminePink.toArgb())
                .withCircleStrokeOpacity(0.32)
                .withCircleStrokeWidth(1.0)
            circleAnnotationOptionsList.add(circleAnnotationOptions)

            selectedPointAnnotationManager.create(circleAnnotationOptionsList)
        }
    }
}
