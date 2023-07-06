package com.epmedu.animeal.feeding.presentation.model

import android.os.Parcelable
import androidx.compose.runtime.Stable
import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import com.epmedu.animeal.feeding.domain.model.enum.Remoteness
import com.epmedu.animeal.feeding.presentation.model.MapLocation.Companion.toPoint
import com.epmedu.animeal.foundation.tabs.model.AnimalType
import com.epmedu.animeal.resources.R
import com.mapbox.geojson.Point
import com.mapbox.turf.TurfMeasurement
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Stable
@Parcelize
data class FeedingPointModel(
    val id: String,
    val title: String,
    val description: String,
    val city: String,
    val feedStatus: FeedStatus,
    val animalType: AnimalType,
    val isFavourite: Boolean = false,
    val remoteness: Remoteness = Remoteness.ANY,
    val coordinates: Point,
    val image: String = "",
    val feedings: @RawValue List<Feeding>? = null
) : Parcelable {

    constructor(feedingPoint: FeedingPoint) : this(
        feedingPoint.id,
        feedingPoint.title,
        feedingPoint.description,
        feedingPoint.city,
        feedingPoint.animalStatus.toFeedStatus(),
        feedingPoint.animalType,
        feedingPoint.isFavourite,
        feedingPoint.remoteness,
        Point.fromLngLat(feedingPoint.location.longitude, feedingPoint.location.latitude),
        feedingPoint.images[0]
    )

    fun getDrawableRes(): Int =
        when {
            isFavourite -> {
                when (feedStatus) {
                    FeedStatus.RED -> R.drawable.ic_favstate_favouritehungry_high
                    FeedStatus.YELLOW -> R.drawable.ic_favstate_favouritehungry_in_process
                    FeedStatus.GREEN -> R.drawable.ic_favstate_favouritehungry_low
                }
            }
            animalType == AnimalType.Dogs -> {
                when (feedStatus) {
                    FeedStatus.RED -> R.drawable.ic_dogsstate_doghungry_high
                    FeedStatus.YELLOW -> R.drawable.ic_dogsstate_doghungry_in_process
                    FeedStatus.GREEN -> R.drawable.ic_dogsstate_doghungry_low
                }
            }
            // animalType == AnimalType.Cats
            else -> {
                when (feedStatus) {
                    FeedStatus.RED -> R.drawable.ic_catsstate_cathungry_high
                    FeedStatus.YELLOW -> R.drawable.ic_catsstate_cathungry_in_process
                    FeedStatus.GREEN -> R.drawable.ic_catsstate_cathungry_low
                }
            }
        }

    companion object {
        private const val NEAREST_DISTANCE_KM = 10
        fun FeedingPointModel.isNearTo(location: MapLocation): Boolean =
            TurfMeasurement.distance(location.toPoint(), this.coordinates) < NEAREST_DISTANCE_KM
    }
}
