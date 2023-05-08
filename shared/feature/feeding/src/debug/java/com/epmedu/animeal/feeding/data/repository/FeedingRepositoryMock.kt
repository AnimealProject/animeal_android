package com.epmedu.animeal.feeding.data.repository

import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.feeding.domain.model.Feeding
import com.epmedu.animeal.feeding.domain.repository.FeedingRepository

internal class FeedingRepositoryMock : FeedingRepository {

    override suspend fun getUserFeedings(): List<Feeding> {
        return emptyList()
    }

    override suspend fun startFeeding(feedingPointId: String): ActionResult {
        return ActionResult.Success
    }

    override suspend fun cancelFeeding(feedingPointId: String): ActionResult {
        return ActionResult.Success
    }

    override suspend fun rejectFeeding(feedingPointId: String, reason: String): ActionResult {
        return ActionResult.Success
    }

    override suspend fun finishFeeding(
        feedingPointId: String,
        images: List<String>
    ): ActionResult {
        return ActionResult.Success
    }
}