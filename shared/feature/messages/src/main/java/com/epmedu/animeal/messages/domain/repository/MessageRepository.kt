package com.epmedu.animeal.messages.domain.repository

import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.messages.domain.model.Message
import kotlinx.coroutines.flow.Flow

@Suppress("ComplexInterface")
interface MessageRepository {

    fun getAllMessages(shouldFetch: Boolean = false): Flow<List<Message>>

    suspend fun removeMessage(messageId: String): ActionResult<Unit>

    suspend fun createFeedingPointIssue(
        feedingPointId: String,
        body: String,
        images: List<String>
    ): ActionResult<Unit>
}