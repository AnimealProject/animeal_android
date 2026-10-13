package com.epmedu.animeal.messages.data.repository

import com.epmedu.animeal.api.message.MessageApi
import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.messages.data.mapper.toActionResult
import com.epmedu.animeal.messages.domain.model.Message
import com.epmedu.animeal.messages.domain.repository.MessageRepository
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

internal class MessageRepositoryImpl @Inject constructor(
    private val messageApi: MessageApi
) : MessageRepository {

    private val messagesFlow = MutableSharedFlow<List<Message>>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private var cachedMessagesMap = mutableMapOf<String, Message>()

    override fun getAllMessages(shouldFetch: Boolean): Flow<List<Message>> {
        return when {
            shouldFetch -> {
                merge(
                    fetchAllMessages()
                        .onEach { messages ->
                            cachedMessagesMap = messages.associateBy { it.id }.toMutableMap()
                        }
                ).onEach { messages ->
                    messagesFlow.emit(messages)
                }
            }

            else -> {
                messagesFlow.asSharedFlow()
            }
        }
    }

    override suspend fun removeMessage(messageId: String): ActionResult<Unit> {
        return ActionResult.Success(Unit)
    }

    override suspend fun createFeedingPointIssue(
        feedingPointId: String,
        body: String,
        images: List<String>
    ): ActionResult<Unit> {
        return messageApi.createFeedingPointIssue(feedingPointId, body, images).toActionResult(feedingPointId)
    }

    private fun fetchAllMessages(): Flow<List<Message>> {
        return flow {
            val messages = listOf<Message>()
            emit(messages)
        }
    }
}