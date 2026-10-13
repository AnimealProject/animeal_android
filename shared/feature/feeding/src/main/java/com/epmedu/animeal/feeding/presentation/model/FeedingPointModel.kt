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
    val code: String,
    val title: String,
    val description: String,
    val city: String,
    val address: String,
    val feedStatus: FeedStatus,
    val inactive: Boolean,
    val animalType: AnimalType,
    val isFavourite: Boolean = false,
    val coordinates: Point,
    val image: NetworkFile? = null,
    val feedings: @RawValue List<Feeding>? = null,
    val assignedModerators: List<String>? = null
) : Parcelable {

    constructor(feedingPoint: FeedingPoint) : this(
        id = feedingPoint.id,
        code = feedingPoint.code,
        title = feedingPoint.title,
        description = feedingPoint.description,
        city = feedingPoint.city,
        address = feedingPoint.address,
        feedStatus = feedingPoint.animalStatus.toFeedStatus(),
        inactive = feedingPoint.inactive,
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

    private fun getGeneralDrawableRes(): Int =
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

    fun getDrawableRes(): Int =
        if (!inactive) {
            getGeneralDrawableRes()
        } else {
            when {
                isFavourite -> R.drawable.ic_favstate_favouritehungry_inactive
                animalType == AnimalType.Dogs -> R.drawable.ic_dogsstate_doghungry_inactive
                else -> R.drawable.ic_catsstate_cathungry_inactive
            }
        }
}
