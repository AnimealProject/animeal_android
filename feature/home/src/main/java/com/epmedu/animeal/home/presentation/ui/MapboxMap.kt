package com.epmedu.animeal.home.presentation.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.epmedu.animeal.home.presentation.model.FeedingPointUi
import com.epmedu.animeal.home.presentation.model.MapLocation
import com.epmedu.animeal.home.presentation.viewmodel.HomeViewModel
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.ResourceOptions
import com.mapbox.maps.plugin.annotation.generated.OnPointAnnotationClickListener
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.scalebar.scalebar

@Composable
fun MapboxMap(
    homeViewModel: HomeViewModel,
    onFeedingPointClick: (point: FeedingPointUi) -> Unit
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val state by homeViewModel.stateFlow.collectAsState()
    val mapBoxView = mapView(state.mapBoxPublicKey, state.mapBoxStyleUri)
    val markerController by remember { mutableStateOf(MarkerController(mapBoxView)) }

    LaunchedEffect(Unit) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            homeViewModel.getFeedingPointsFlow().collect { pointsState ->
                addMarkers(
                    pointsState.feedingPoints,
                    onFeedingPointClick,
                    markerController
                )
            }
        }
    }

    AndroidView(
        factory = { mapBoxView },
        modifier = Modifier.fillMaxSize()
    ) { mapView ->
        mapView.scalebar.enabled = false
        mapView.location.updateSettings {
            enabled = true
            pulsingEnabled = true
        }

        mapView.showInitialLocation(state.currentLocation)
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

private fun MapView.showInitialLocation(location: MapLocation) = getMapboxMap().setCamera(
    CameraOptions.Builder()
        .zoom(13.0)
        .center(Point.fromLngLat(location.longitude, location.latitude))
        .build()
)

/**
 * Creates a list of markers in the map
 *
 * @param feedingPoints the list of feeding points
 * @param onFeedingPointClick marker click listener
 * @param markerController class that manages image cache and markers creation/deletion
 *
 * @return returns the manager that will allow us to update or delete the markers created
 */
private fun addMarkers(
    feedingPoints: List<FeedingPointUi>,
    onFeedingPointClick: (point: FeedingPointUi) -> Unit,
    markerController: MarkerController
): PointAnnotationManager {
    markerController.pointAnnotationManager.deleteAll()
    val pointAnnotationManager = markerController.pointAnnotationManager

    feedingPoints.forEach { feedingPoint ->
        val icon = markerController.markerCache.getMarker(feedingPoint.getDrawableRes())
        // Set options for the resulting symbol layer.
        val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
            .withPoint(feedingPoint.coordinates)
            // Specify the bitmap you assigned to the point annotation
            // The bitmap will be added to map style automatically.
            .withIconImage(icon)
        pointAnnotationManager.create(pointAnnotationOptions)
    }

    pointAnnotationManager.addClickListener(
        OnPointAnnotationClickListener {
            feedingPoints.find { feedingPointUi ->
                feedingPointUi.coordinates == it.point
            }?.let { point ->
                onFeedingPointClick(point)
            }
            true
        }
    )

    return pointAnnotationManager
}