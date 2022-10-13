package com.epmedu.animeal.home.presentation.model

import androidx.compose.runtime.Stable
import com.epmedu.animeal.foundation.switch.AnimalType
import com.epmedu.animeal.home.data.model.Feeder
import com.epmedu.animeal.home.data.model.FeedingPoint
import com.epmedu.animeal.home.data.model.enum.AnimalPriority
import com.epmedu.animeal.home.data.model.enum.Remoteness
import com.epmedu.animeal.home.presentation.model.FeedStatus.Companion.toFeedStatus
import com.epmedu.animeal.resources.R
import com.mapbox.geojson.Point

@Stable
data class FeedingPointUi(
    val id: Int, // For future implementations
    val title: String,
    val description: String,
    val animalPriority: AnimalPriority,
    val feedStatus: FeedStatus,
    val animalType: AnimalType,
    val isFavourite: Boolean = false,
    val lastFeeder: Feeder,
    val remoteness: Remoteness = Remoteness.ANY,
    val coordinates: Point
) {

    constructor(feedingPoint: FeedingPoint) : this(
        feedingPoint.id,
        feedingPoint.title,
        feedingPoint.description,
        feedingPoint.animalPriority,
        feedingPoint.animalStatus.toFeedStatus(),
        feedingPoint.animalType,
        feedingPoint.isFavourite,
        feedingPoint.lastFeeder,
        feedingPoint.remoteness,
        Point.fromLngLat(feedingPoint.location.longitude, feedingPoint.location.latitude)
    )

    fun getDrawableRes(): Int =
        when {
            isFavourite -> {
                when (feedStatus) {
                    FeedStatus.RED -> R.drawable.ic_favstate_favouritehungry_high
                    FeedStatus.ORANGE -> R.drawable.ic_favstate_favouritehungry_medium
                    FeedStatus.GREEN -> R.drawable.ic_favstate_favouritehungry_low
                }
            }
            animalType == AnimalType.Dogs -> {
                when (feedStatus) {
                    FeedStatus.RED -> R.drawable.ic_dogsstate_doghungry_high
                    FeedStatus.ORANGE -> R.drawable.ic_dogsstate_doghungry_medium
                    FeedStatus.GREEN -> R.drawable.ic_dogsstate_doghungry_low
                }
            }
            // animalType == AnimalType.Cats
            else ->
                when (feedStatus) {
                    FeedStatus.RED -> R.drawable.ic_catsstate_cathungry_high
                    FeedStatus.ORANGE -> R.drawable.ic_catsstate_cathungry_medium
                    FeedStatus.GREEN -> R.drawable.ic_catsstate_cathungry_low
                }
        }
}
