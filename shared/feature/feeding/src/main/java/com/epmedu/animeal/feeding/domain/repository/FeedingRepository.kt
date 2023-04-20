package com.epmedu.animeal.feeding.domain.repository

import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.feeding.domain.model.Feeding

interface FeedingRepository {

    suspend fun getUserFeedings(): List<Feeding>

    suspend fun startFeeding(feedingPointId: String): ActionResult

    suspend fun cancelFeeding(feedingPointId: String): ActionResult

    suspend fun rejectFeeding(feedingPointId: String, reason: String): ActionResult

    suspend fun finishFeeding(feedingPointId: String, images: List<String>): ActionResult
}