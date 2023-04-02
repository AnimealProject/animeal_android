package com.epmedu.animeal.feeding.domain.repository

import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import kotlinx.coroutines.flow.Flow

interface FeedingPointRepository {

    fun getAllFeedingPoints(): Flow<List<FeedingPoint>>

    fun getFeedingPointsBy(predicate: (FeedingPoint) -> Boolean): Flow<List<FeedingPoint>>

    suspend fun startFeeding(feedingPointId: String): ActionResult

    suspend fun cancelFeeding(feedingPointId: String): ActionResult

    suspend fun rejectFeeding(feedingPointId: String, reason: String): ActionResult

    suspend fun finishFeeding(feedingPointId: String, images: List<String>): ActionResult
}