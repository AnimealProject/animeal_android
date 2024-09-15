package com.epmedu.animeal.feeding.presentation.model

import android.os.Parcelable
import androidx.compose.runtime.Stable
import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import com.epmedu.animeal.feeding.presentation.model.FeedStatus.Fed
import com.epmedu.animeal.feeding.presentation.model.FeedStatus.InProgress
import com.epmedu.animeal.feeding.presentation.model.FeedStatus.Pending
import com.epmedu.animeal.feeding.presentation.model.FeedStatus.Starved
import com.epmedu.animeal.foundation.tabs.model.AnimalType
import com.epmedu.animeal.networkstorage.domain.NetworkFile
import com.epmedu.animeal.resources.R
import com.mapbox.geojson.Point
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
    val coordinates: Point,
    val image: NetworkFile? = null,
    val feedings: @RawValue List<Feeding>? = null,
    val assignedModerators: List<String>? = null
) : Parcelable {

    constructor(feedingPoint: FeedingPoint) : this(
        id = feedingPoint.id,
        title = feedingPoint.title,
        description = feedingPoint.description,
        city = feedingPoint.city,
        feedStatus = feedingPoint.animalStatus.toFeedStatus(),
        animalType = feedingPoint.animalType,
        isFavourite = feedingPoint.isFavourite,
        coordinates = Point.fromLngLat(
            feedingPoint.location.longitude,
            feedingPoint.location.latitude
        ),
        image = feedingPoint.image,
        assignedModerators = feedingPoint.assignedModerators?.map { moderator ->
            "${moderator.name} ${moderator.surname}"
        }
    )

    fun getDrawableRes(): Int =
        when {
            isFavourite -> {
                when (feedStatus) {
                    Starved -> R.drawable.ic_favstate_favouritehungry_high
                    InProgress, Pending -> R.drawable.ic_favstate_favouritehungry_in_process
                    Fed -> R.drawable.ic_favstate_favouritehungry_low
                }
            }

            animalType == AnimalType.Dogs -> {
                when (feedStatus) {
                    Starved -> R.drawable.ic_dogsstate_doghungry_high
                    InProgress, Pending -> R.drawable.ic_dogsstate_doghungry_in_process
                    Fed -> R.drawable.ic_dogsstate_doghungry_low
                }
            }
            // animalType == AnimalType.Cats
            else -> {
                when (feedStatus) {
                    Starved -> R.drawable.ic_catsstate_cathungry_high
                    InProgress, Pending -> R.drawable.ic_catsstate_cathungry_in_process
                    Fed -> R.drawable.ic_catsstate_cathungry_low
                }
            }
        }
}
