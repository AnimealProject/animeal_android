package com.epmedu.animeal.feeding.domain.repository

import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.feeding.domain.model.FeedingHistory
import com.epmedu.animeal.feeding.domain.model.UserFeeding
import kotlinx.coroutines.flow.Flow

interface FeedingRepository {

    suspend fun getUserFeedings(): List<UserFeeding>

    fun getFeedingHistories(feedingPointId: String): Flow<List<FeedingHistory>>

    suspend fun startFeeding(feedingPointId: String): ActionResult

    suspend fun cancelFeeding(feedingPointId: String): ActionResult

    suspend fun rejectFeeding(feedingPointId: String, reason: String): ActionResult

    suspend fun finishFeeding(feedingPointId: String, images: List<String>): ActionResult
}