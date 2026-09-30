package com.epmedu.animeal.messages.domain.usecase

import com.epmedu.animeal.messages.domain.model.Message
import com.epmedu.animeal.messages.domain.repository.MessageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllMessagesUseCase @Inject constructor(
    private val repository: MessageRepository
) {
    operator fun invoke(shouldFetch: Boolean = false): Flow<List<Message>> =
        repository.getAllMessages(shouldFetch).map(::sortByDate)

    private fun sortByDate(feedings: List<Message>): List<Message> {
        return feedings.sortedByDescending { item -> item.createdAt }
    }
}