package com.epmedu.animeal.model

import com.epmedu.animeal.common.data.enum.AnimalPriority
import com.epmedu.animeal.common.data.enum.AnimalState
import com.epmedu.animeal.common.data.enum.Remoteness
import com.epmedu.animeal.common.data.model.FeedingPoint
import com.epmedu.animeal.foundation.switch.AnimalType
import com.epmedu.animeal.resources.R
import com.mapbox.geojson.Point

data class FeedingPointUi(
    val id: Int, // For future implementations
    val priority: AnimalPriority,
    val status: AnimalState,
    val animalType: AnimalType,
    val isFavourite: Boolean = false,
    val remoteness: Remoteness = Remoteness.ANY,
    var coordinates: Point
) {

    constructor(feedingPoint: FeedingPoint) : this(
        feedingPoint.id,
        feedingPoint.animalPriority,
        feedingPoint.animalStatus,
        feedingPoint.animalType,
        feedingPoint.isFavourite,
        feedingPoint.remoteness,
        Point.fromLngLat(feedingPoint.location.latitude, feedingPoint.location.longitude)
    )

    fun getDrawableRes(): Int =
        when {
            isFavourite -> {
                when (status) {
                    AnimalState.RED -> R.drawable.ic_favstate_favouritehungry_high
                    AnimalState.YELLOW -> R.drawable.ic_favstate_favouritehungry_medium
                    AnimalState.GREEN -> R.drawable.ic_favstate_favouritehungry_low
                }
            }
            animalType == AnimalType.Dogs -> {
                when (status) {
                    AnimalState.RED -> R.drawable.ic_dogsstate_doghungry_high
                    AnimalState.YELLOW -> R.drawable.ic_dogsstate_doghungry_medium
                    AnimalState.GREEN -> R.drawable.ic_dogsstate_doghungry_low
                }
            }
            //animalType == AnimalType.Cats
            else ->
                when (status) {
                    AnimalState.RED -> R.drawable.ic_catsstate_cathungry_high
                    AnimalState.YELLOW -> R.drawable.ic_catsstate_cathungry_medium
                    AnimalState.GREEN -> R.drawable.ic_catsstate_cathungry_low
                }
        }
}
