package com.epmedu.animeal.model

import com.epmedu.animeal.resources.R
import com.mapbox.geojson.Point

data class FeedingPoint(
    val id: Int, // For future implementations
    val priority: Int,
    val status: State,
    val animalType: AnimalType,
    val isFavourite: Boolean = false,
    val remoteness: Remoteness = Remoteness.ANY,
    var coordinates: Point
) {

    fun getDrawableRes(): Int =
        when {
            isFavourite -> {
                when (status) {
                    State.RED -> R.drawable.ic_favstate_favouritehungry_high
                    State.YELLOW -> R.drawable.ic_favstate_favouritehungry_medium
                    State.GREEN -> R.drawable.ic_favstate_favouritehungry_low
                }
            }
            animalType == AnimalType.DOG -> {
                when (status) {
                    State.RED -> R.drawable.ic_dogsstate_doghungry_high
                    State.YELLOW -> R.drawable.ic_dogsstate_doghungry_medium
                    State.GREEN -> R.drawable.ic_dogsstate_doghungry_low
                }
            }
            else -> {
                when (status) {
                    State.RED -> R.drawable.ic_catsstate_cathungry_high
                    State.YELLOW -> R.drawable.ic_catsstate_cathungry_medium
                    State.GREEN -> R.drawable.ic_catsstate_cathungry_low
                }
            }
        }
}

enum class State {
    RED, GREEN, YELLOW
}

enum class Remoteness {
    NEAREST, ANY, NEAREST_10_KM
}

enum class AnimalType {
    CAT, DOG
}