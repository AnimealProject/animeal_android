package com.epmedu.animeal.home.presentation.ui.map

import com.epmedu.animeal.extensions.consume
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.home.utils.MarkerCache
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager

class MarkerController(
    private val onFeedingPointClick: (point: FeedingPointModel) -> Unit,
    mapView: MapView
) {
    private val markerCache = MarkerCache(mapView.context)
    private val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
    private val currentFeedingPoints = mutableListOf<FeedingPointModel>()

    private val onPointClickListener: (PointAnnotation) -> Boolean = { pointAnnotation ->
        consume {
            currentFeedingPoints.find { pointAnnotation.point == it.coordinates }?.let {
                onFeedingPointClick(it)
            }
        }
    }

    fun drawMarkers(feedingPoints: List<FeedingPointModel>) {
        val pointAnnotationOptionsList = mutableListOf<PointAnnotationOptions>()

        pointAnnotationManager.deleteAll()
        pointAnnotationManager.removeClickListener(onPointClickListener)

        currentFeedingPoints.clear()
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
}
