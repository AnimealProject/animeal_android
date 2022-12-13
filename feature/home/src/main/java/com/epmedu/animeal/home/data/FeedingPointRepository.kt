package com.epmedu.animeal.home.data

import com.epmedu.animeal.home.data.model.FeedingPoint
import com.epmedu.animeal.profile.data.model.Profile
import kotlinx.coroutines.flow.Flow

interface FeedingPointRepository {

    fun getAllFeedingPoints(): Flow<List<FeedingPoint>>

    fun getCats(): Flow<List<FeedingPoint>>

    fun getDogs(): Flow<List<FeedingPoint>>

    fun getFavourites(): Flow<List<FeedingPoint>>

    fun getFeedingPoint(id: Int): Flow<FeedingPoint>

    fun saveUserAsCurrentFeeder(user: Profile, feedingPointId: Int): Flow<Boolean>
}