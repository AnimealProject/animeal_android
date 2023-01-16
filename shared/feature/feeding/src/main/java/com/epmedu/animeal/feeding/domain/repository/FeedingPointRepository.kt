package com.epmedu.animeal.feeding.domain.repository

import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import com.epmedu.animeal.profile.data.model.Profile
import kotlinx.coroutines.flow.Flow

interface FeedingPointRepository {

    fun getAllFeedingPoints(): Flow<List<FeedingPoint>>

    fun getCats(): Flow<List<FeedingPoint>>

    fun getDogs(): Flow<List<FeedingPoint>>

    fun getFavourites(): Flow<List<FeedingPoint>>

    fun getFeedingPoint(id: String): Flow<FeedingPoint?>

    fun saveUserAsCurrentFeeder(user: Profile, feedingPointId: String): Flow<Boolean>
}