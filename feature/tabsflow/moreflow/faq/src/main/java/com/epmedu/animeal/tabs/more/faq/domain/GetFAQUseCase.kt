package com.epmedu.animeal.tabs.more.faq.domain

import com.epmedu.animeal.tabs.more.faq.domain.model.FrequentlyAskedQuestion
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class GetFAQUseCase(
    private val repository: FAQRepository
) {
    operator fun invoke(): Flow<ImmutableList<FrequentlyAskedQuestion>> =
        repository.getFAQ().map { list ->
            list.map { item -> item.copy(question = item.question.trimStart()) }.toImmutableList()
        }
}
