package com.epmedu.animeal.feeding.data.repository

import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.feeding.domain.model.DomainFeedState
import com.epmedu.animeal.feeding.domain.model.FeedingHistory
import com.epmedu.animeal.feeding.domain.model.FeedingInProgress
import com.epmedu.animeal.feeding.domain.model.UserFeeding
import com.epmedu.animeal.feeding.domain.repository.FeedingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

internal class FeedingRepositoryMock : FeedingRepository {

    override fun getFeedStateFlow(): Flow<DomainFeedState> {
        return flowOf(DomainFeedState(null))
    }

    override suspend fun getUserFeedings(): List<UserFeeding> {
        return emptyList()
    }

    override fun getFeedingInProgress(feedingPointId: String): Flow<FeedingInProgress?> {
        return flowOf(null)
    }

    override fun getFeedingHistories(feedingPointId: String, status: String?): Flow<List<FeedingHistory>> {
        return flowOf(emptyList())
    }

    override suspend fun startFeeding(feedingPointId: String): ActionResult<Unit> {
        return ActionResult.Success(Unit)
    }

    override suspend fun cancelFeeding(feedingPointId: String): ActionResult<Unit> {
        return ActionResult.Success(Unit)
    }

    override suspend fun rejectFeeding(feedingPointId: String, reason: String): ActionResult<Unit> {
        return ActionResult.Success(Unit)
    }

    override suspend fun finishFeeding(
        feedingPointId: String,
        images: List<String>
    ): ActionResult<Unit> {
        return ActionResult.Success(Unit)
    }

    override suspend fun updateFeedStateFlow(newFeedState: DomainFeedState) {}

}