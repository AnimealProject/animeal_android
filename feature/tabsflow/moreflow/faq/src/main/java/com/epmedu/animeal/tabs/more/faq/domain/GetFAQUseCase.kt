package com.epmedu.animeal.tabs.more.faq.domain

import com.epmedu.animeal.tabs.more.faq.presentation.model.FrequentlyAskedQuestionCard
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class GetFAQUseCase(
    private val repository: FAQRepository
) {
    operator fun invoke(): Flow<ImmutableList<FrequentlyAskedQuestionCard>> {
        return repository.getFAQ().map { questions ->
            questions.map { frequentlyAskedQuestion ->
                FrequentlyAskedQuestionCard(
                    question = frequentlyAskedQuestion.question,
                    answer = frequentlyAskedQuestion.answer
                )
            }.toImmutableList()
        }
    }
}