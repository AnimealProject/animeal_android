package com.epmedu.animeal.home.presentation.ui.map

import androidx.core.graphics.drawable.toBitmap
import com.epmedu.animeal.extensions.drawableCompat
import com.epmedu.animeal.feeding.presentation.model.FeedStatus
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.resources.R
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.RenderedQueryGeometry
import com.mapbox.maps.RenderedQueryOptions
import com.mapbox.maps.Style
import com.mapbox.maps.StyleLoadedCallback
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.all
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.eq
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.get
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.gt
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.has
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.literal
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.neq
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.step
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.SymbolLayer
import com.mapbox.maps.extension.style.layers.generated.circleLayer
import com.mapbox.maps.extension.style.layers.generated.symbolLayer
import com.mapbox.maps.extension.style.layers.getLayer
import com.mapbox.maps.extension.style.layers.properties.generated.CircleTranslateAnchor
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.sources.getSource
import com.mapbox.maps.extension.style.sources.getSourceAs
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.gestures

class MarkerController(
    private val onFeedingPointClick: (point: FeedingPointModel) -> Unit,
    private val onMapClick: (Point) -> Unit,
    private val mapView: MapView,
    styleLoadedCallback: StyleLoadedCallback
) {
    private val currentFeedingPoints = mutableListOf<FeedingPointModel>()

    init {
        mapView.mapboxMap.getStyle { style -> initLayers(style) }
        mapView.mapboxMap.subscribeStyleLoaded(styleLoadedCallback)
    }

    private val mapClickListener = OnMapClickListener { point ->
        val screenPoint = mapView.mapboxMap.pixelForCoordinate(point)

        mapView.mapboxMap.queryRenderedFeatures(
            RenderedQueryGeometry(screenPoint),
            RenderedQueryOptions(listOf(FeedingCluster.ICON_NAME, FeedingPoint.ICON_NAME), null)
        ) { result ->
            val feature = result.value
                ?.takeIf { result.isValue }
                ?.firstOrNull()
                ?.queriedFeature
                ?.feature

            when {
                feature == null -> {
                    onMapClick(point)
                }

                feature.getBooleanProperty("cluster") == true -> {
                    mapView.zoomToCluster(FEEDING_SOURCE_NAME, feature)
                }

                else -> {
                    val id = feature.getStringProperty(FeatureKey.ID)
                    val feedingPoint = currentFeedingPoints.firstOrNull { it.id == id }
                    if (feedingPoint != null) {
                        mapView.zoomTo(point)
                        onFeedingPointClick(feedingPoint)
                    } else {
                        onMapClick(point)
                    }
                }
            }
        }

        true
    }

    @Suppress("LongMethod")
    private fun initLayers(style: Style) {
        if (style.getSource(FEEDING_SOURCE_NAME) != null) {
            return
        }

        val source = geoJsonSource(FEEDING_SOURCE_NAME) {
            featureCollection(FeatureCollection.fromFeatures(listOf()))
            cluster(true)
            clusterRadius(40)
            clusterMaxZoom(24)

            clusterProperties(
                hashMapOf(
                    STARVED_COUNT_NAME to arrayOf(
                        "+",
                        arrayOf(
                            "case",
                            arrayOf("==", arrayOf("get", FeatureKey.IS_STARVED), true),
                            1,
                            0
                        )
                    )
                )
            )
        }

        style.addSource(source)

        arrayOf(
            R.drawable.ic_dogsstate_doghungry_high,
            R.drawable.ic_dogsstate_doghungry_low,
            R.drawable.ic_dogsstate_doghungry_in_process,
            R.drawable.ic_dogsstate_doghungry_inactive,
            R.drawable.ic_catsstate_cathungry_high,
            R.drawable.ic_catsstate_cathungry_low,
            R.drawable.ic_catsstate_cathungry_in_process,
            R.drawable.ic_catsstate_cathungry_inactive
        ).forEach { id ->
            val bitmap = mapView.context.drawableCompat(id).toBitmap()
            style.addImage(getIconName(id), bitmap)
        }

        val clusterLayer = circleLayer(FeedingCluster.ICON_NAME, FEEDING_SOURCE_NAME) {
            circleColor(FeedingCluster.COLOR)
            circleRadius(
                step(
                    get("point_count"),
                    literal(15),
                    literal(30) to literal(20),
                    literal(50) to literal(30)
                )
            )
            circleStrokeColor(FeedingCluster.STROKE_COLOR)
            circleStrokeWidth(2.5)
        }

        val clusterCountLayer = symbolLayer(FeedingCluster.TEXT_NAME, FEEDING_SOURCE_NAME) {
            textField(get("point_count"))
            textSize(16.0)
            textColor(FeedingCluster.TEXT_COLOR)
        }

        val pointLayer = symbolLayer(FeedingPoint.ICON_NAME, FEEDING_SOURCE_NAME) {
            iconImage(get(FeatureKey.ICON))
            filter(
                neq(get("cluster"), literal(true))
            )
        }

        val selectedLayer = symbolLayer(FeedingPoint.SELECTED_NAME, FEEDING_SOURCE_NAME) {
            iconImage(get(FeatureKey.ICON))
            iconSize(1.5)
            filter(eq(get(FeatureKey.ID), literal("")))
        }

        val starvedFilter = all(
            has(STARVED_COUNT_NAME),
            gt(get(STARVED_COUNT_NAME), literal(0))
        )

        val starvedBadgeCircleLayer = circleLayer(FeedingClusterBadge.BG_NAME, FEEDING_SOURCE_NAME) {
            filter(starvedFilter)

            circleColor(FeedingClusterBadge.COLOR)
            circleRadius(
                step(
                    get(STARVED_COUNT_NAME),
                    literal(8),
                    literal(10) to literal(10),
                    literal(100) to literal(12)
                )
            )
            circleStrokeColor(FeedingClusterBadge.STROKE_COLOR)
            circleStrokeWidth(1.5)

            circleTranslate(listOf(14.0, -14.0))
            circleTranslateAnchor(CircleTranslateAnchor.MAP)
        }

        val starvedBadgeLayer = symbolLayer(FeedingClusterBadge.TEXT_NAME, FEEDING_SOURCE_NAME) {
            filter(starvedFilter)

            textField(get(STARVED_COUNT_NAME))
            textSize(10.0)
            textColor(FeedingClusterBadge.TEXT_COLOR)

            textTranslate(listOf(14.0, -14.0))
            textAllowOverlap(true)
            textIgnorePlacement(true)
        }

        style.addLayer(clusterLayer)
        style.addLayer(clusterCountLayer)

        style.addLayer(starvedBadgeCircleLayer)
        style.addLayer(starvedBadgeLayer)

        style.addLayer(pointLayer)
        style.addLayer(selectedLayer)
    }

    private fun getIconName(resourceId: Int) = "fp_icon_$resourceId"

    fun drawMarkers(feedingPoints: List<FeedingPointModel>) {
        mapView.gestures.removeOnMapClickListener(mapClickListener)

        currentFeedingPoints.clear()
        currentFeedingPoints.addAll(feedingPoints)

        val features = feedingPoints.map { feedingPoint ->
            Feature.fromGeometry(feedingPoint.coordinates).apply {
                addStringProperty(FeatureKey.ICON, getIconName(feedingPoint.getDrawableRes()))
                addStringProperty(FeatureKey.ID, feedingPoint.id)
                addBooleanProperty(
                    FeatureKey.IS_STARVED,
                    feedingPoint.feedStatus == FeedStatus.Starved &&
                        !feedingPoint.inactive
                )
            }
        }

        mapView.mapboxMap.getStyle { style ->
            val source = style.getSourceAs<GeoJsonSource>(FEEDING_SOURCE_NAME)
            source?.featureCollection(
                FeatureCollection.fromFeatures(features)
            )
        }

        mapView.gestures.addOnMapClickListener(mapClickListener)
    }

    fun selectMarker(feedingPoint: FeedingPointModel?) {
        mapView.mapboxMap.getStyle { style ->
            val layer = style.getLayer(FeedingPoint.SELECTED_NAME) as SymbolLayer
            layer.filter(
                eq(get(FeatureKey.ID), literal(feedingPoint?.id ?: ""))
            )
        }
    }

    companion object {
        const val FEEDING_SOURCE_NAME = "feeding-points-source"
        const val STARVED_COUNT_NAME = "starved_count"
        const val COLOR_WHITE = "#FFFFFF"

        object FeatureKey {
            const val ID = "id"
            const val ICON = "icon"
            const val IS_STARVED = "isStarved"
        }

        object FeedingPoint {
            const val ICON_NAME = "feeding-point-icon"
            const val SELECTED_NAME = "feeding-point-bg"
        }

        object FeedingCluster {
            const val TEXT_NAME = "feeding-cluster-text"
            const val ICON_NAME = "feeding-cluster-bg"
            const val COLOR = "#03BFD7"
            const val STROKE_COLOR = COLOR_WHITE
            const val TEXT_COLOR = COLOR_WHITE
        }

        object FeedingClusterBadge {
            const val TEXT_NAME = "feeding-cluster-badge-text"
            const val BG_NAME = "feeding-cluster-badge-bg"
            const val COLOR = "#EA4335"
            const val STROKE_COLOR = "#C5221F"
            const val TEXT_COLOR = COLOR_WHITE
        }
    }
}
