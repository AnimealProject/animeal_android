package com.epmedu.animeal.messages.domain.usecase

import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.messages.domain.repository.MessageRepository
import javax.inject.Inject

class SendIssueMessageUseCase @Inject constructor(
    private val repository: MessageRepository,
) {

    suspend operator fun invoke(
        feedingPointId: String,
        body: String,
        images: List<String>
    ): ActionResult<Unit> {
        return repository.createFeedingPointIssue(feedingPointId, body, images)
    }
}