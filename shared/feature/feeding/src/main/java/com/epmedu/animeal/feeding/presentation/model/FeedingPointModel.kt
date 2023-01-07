package com.epmedu.animeal.feeding.presentation.model

import androidx.compose.runtime.Stable
import com.epmedu.animeal.feeding.domain.model.Feeder
import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import com.epmedu.animeal.feeding.domain.model.enum.Remoteness
import com.epmedu.animeal.foundation.switch.AnimalType
import com.epmedu.animeal.resources.R
import com.mapbox.geojson.Point

@Stable
data class FeedingPointModel(
    val id: String,
    val title: String,
    val description: String,
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
                    FeedStatus.GREEN -> R.drawable.ic_favstate_favouritehungry_low
                }
            }
            animalType == AnimalType.Dogs -> {
                when (feedStatus) {
                    FeedStatus.RED -> R.drawable.ic_dogsstate_doghungry_high
                    FeedStatus.GREEN -> R.drawable.ic_dogsstate_doghungry_low
                }
            }
            // animalType == AnimalType.Cats
            else ->
                when (feedStatus) {
                    FeedStatus.RED -> R.drawable.ic_catsstate_cathungry_high
                    FeedStatus.GREEN -> R.drawable.ic_catsstate_cathungry_low
                }
        }
}
