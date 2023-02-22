package com.epmedu.animeal.tabs.more.faq.data

import com.epmedu.animeal.api.faq.FAQApi
import com.epmedu.animeal.tabs.more.faq.data.mapper.toDomain
import com.epmedu.animeal.tabs.more.faq.domain.FAQRepository
import com.epmedu.animeal.tabs.more.faq.domain.model.FrequentlyAskedQuestion
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class FAQRepositoryImpl(
    private val faqApi: FAQApi
) : FAQRepository {

    override fun getFAQ(): Flow<ImmutableList<FrequentlyAskedQuestion>> {
        return faqApi.getFAQ().map { questions ->
            questions.map { question ->
                question.toDomain()
            }.toImmutableList()
        }
    }
}
