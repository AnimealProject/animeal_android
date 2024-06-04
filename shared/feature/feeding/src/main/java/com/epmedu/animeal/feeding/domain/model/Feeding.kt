package com.epmedu.animeal.feeding.domain.model

import com.epmedu.animeal.networkstorage.domain.NetworkFile
import com.epmedu.animeal.users.domain.model.User
import java.util.Date

data class Feeding(
    val id: String,
    val feeder: User?,
    val status: FeedingStatus,
    val date: Date,
    val feedingPointId: String,
    val photos: List<NetworkFile>,
    val reviewedBy: User? = null,
    val rejectionReason: String? = null
) {
    // id is not unique because it is equal to the feeding point id
    // TODO: Remove when EPMEDU-3783 is implemented
    val temporaryId: String
        get() = "$id-$date"
}
