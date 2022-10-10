package com.epmedu.animeal.home.presentation.ui.map

import com.epmedu.animeal.extensions.consume
import com.epmedu.animeal.home.presentation.model.FeedingPointUi
import com.epmedu.animeal.home.utils.MarkerCache
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager

class MarkerController(
    private val onFeedingPointClick: (point: FeedingPointUi) -> Unit,
    mapView: MapView
) {
    private val markerCache = MarkerCache(mapView.context)
    private val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()

    fun drawMarkers(feedingPoints: List<FeedingPointUi>) {
        pointAnnotationManager.deleteAll()

        feedingPoints.onEach { feedingPoint ->
            val icon = markerCache.getMarker(feedingPoint.getDrawableRes())

            val pointAnnotationOptions = PointAnnotationOptions()
                .withPoint(feedingPoint.coordinates)
                .withIconImage(icon)

            pointAnnotationManager.create(pointAnnotationOptions)
        }

        pointAnnotationManager.addClickListener { pointAnnotation ->
            consume {
                onFeedingPointClick(
                    feedingPoints.single {
                        pointAnnotation.point == it.coordinates
                    }
                )
            }
        }
    }
}
