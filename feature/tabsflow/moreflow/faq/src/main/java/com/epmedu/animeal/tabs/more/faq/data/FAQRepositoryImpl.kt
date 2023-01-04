package com.epmedu.animeal.tabs.more.faq.data

import com.epmedu.animeal.foundation.util.generateLoremIpsum
import com.epmedu.animeal.tabs.more.faq.domain.FAQRepository
import com.epmedu.animeal.tabs.more.faq.domain.model.FrequentlyAskedQuestion
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

internal class FAQRepositoryImpl : FAQRepository {

    private val stubQuestions = List(10) {
        FrequentlyAskedQuestion(
            question = "Question ${it + 1}",
            answer = generateLoremIpsum()
        )
    }.toImmutableList()

    override fun getFAQ(): Flow<ImmutableList<FrequentlyAskedQuestion>> {
        // TODO: Replace with receiving questions from the backend
        return flowOf(stubQuestions)
    }
}
