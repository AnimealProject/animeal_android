package com.epmedu.animeal.feeding.domain.repository

import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.feeding.domain.model.DomainFeedState
import com.epmedu.animeal.feeding.domain.model.FeedingHistory
import com.epmedu.animeal.feeding.domain.model.FeedingInProgress
import com.epmedu.animeal.feeding.domain.model.UserFeeding
import kotlinx.coroutines.flow.Flow

interface FeedingRepository {

    suspend fun getUserFeedings(): List<UserFeeding>

    fun getFeedingInProgress(feedingPointId: String): Flow<FeedingInProgress?>

    fun getFeedingHistories(feedingPointId: String, status: String): Flow<List<FeedingHistory>>

    fun getFeedStateFlow(): Flow<DomainFeedState>

    suspend fun startFeeding(feedingPointId: String): ActionResult<Unit>

    suspend fun cancelFeeding(feedingPointId: String): ActionResult<Unit>

    suspend fun rejectFeeding(feedingPointId: String, reason: String): ActionResult<Unit>

    suspend fun finishFeeding(feedingPointId: String, images: List<String>): ActionResult<Unit>

    suspend fun updateFeedStateFlow(newFeedState: DomainFeedState)


}