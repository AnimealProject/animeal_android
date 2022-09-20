package com.epmedu.animeal.home.presentation.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.epmedu.animeal.home.presentation.OnFeedingPointClickListener
import com.epmedu.animeal.home.presentation.model.FeedingPointUi
import com.epmedu.animeal.home.presentation.viewmodel.HomeState
import com.epmedu.animeal.home.utils.MarkerCache
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.ResourceOptions
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.*
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.scalebar.scalebar

@Composable
fun MapboxMap(
    state: HomeState,
    onFeedingPointClickListener: OnFeedingPointClickListener
) {
    val mapBoxView = mapView(state.mapBoxPublicKey, state.mapBoxStyleUri)

    var initialLocationReceived by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(Unit) { initialLocationReceived = false }

    AndroidView(
        factory = {
            addMarkers(mapBoxView, state.feedingPoints, onFeedingPointClickListener)
            mapBoxView
        },
        modifier = Modifier.fillMaxSize()
    ) { mapView ->
        mapView.scalebar.enabled = false
        mapView.location.updateSettings {
            enabled = true
            pulsingEnabled = true
        }

        if (!initialLocationReceived && !state.currentLocation.isInitial) {
            initialLocationReceived = true
            mapView.getMapboxMap().setCamera(
                CameraOptions.Builder()
                    .zoom(17.0)
                    .center(
                        Point.fromLngLat(
                            state.currentLocation.longitude,
                            state.currentLocation.latitude
                        )
                    ).build()
            )
        }
    }
}

@Composable
private fun mapView(mapboxPublicKey: String, mapBoxStyleUri: String): MapView {
    val context = LocalContext.current
    val mapInitOptions = MapInitOptions(
        context = context,
        resourceOptions = ResourceOptions.Builder().accessToken(mapboxPublicKey).build(),
        styleUri = mapBoxStyleUri
    )

    val mapView = remember(mapInitOptions) {
        MapView(context, mapInitOptions)
    }

    return mapView
}

/**
 * Creates a list of markers in the map
 *
 * @param mapView the view where the markers are being created
 * @param feedingPoints the list of feeding points
 * @param onFeedingPointClickListener marker click listener
 *
 * @return returns the manager that will allow us to update or delete the markers created
 */
private fun addMarkers(
    mapView: MapView,
    feedingPoints: List<FeedingPointUi>,
    onFeedingPointClickListener: OnFeedingPointClickListener
): PointAnnotationManager {
    val annotationApi = mapView.annotations
    val pointAnnotationManager = annotationApi.createPointAnnotationManager()
    val markerCache = MarkerCache(mapView.context)

    feedingPoints.forEach { feedingPoint ->
        markerCache.getVector(feedingPoint.getDrawableRes())
            .let { resourceImg ->
                // Set options for the resulting symbol layer.
                val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
                    .withPoint(feedingPoint.coordinates)
                    // Specify the bitmap you assigned to the point annotation
                    // The bitmap will be added to map style automatically.
                    .withIconImage(resourceImg)
                pointAnnotationManager.create(pointAnnotationOptions)
            }
    }

    pointAnnotationManager.addClickListener(
        OnPointAnnotationClickListener {
            onFeedingPointClickListener.onFeedingPointClick(
                feedingPoints.first { feedingPointUi ->
                    feedingPointUi.coordinates == it.point
                }
            )
            true
        }
    )

    return pointAnnotationManager
}
