package com.epmedu.animeal.tabs.more.faq.data

import com.epmedu.animeal.api.faq.FAQApi
import com.epmedu.animeal.tabs.more.faq.data.mapper.toDomain
import com.epmedu.animeal.tabs.more.faq.domain.FAQRepository
import com.epmedu.animeal.tabs.more.faq.domain.model.FrequentlyAskedQuestion
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class FAQRepositoryImpl(
    private val faqApi: FAQApi
) : FAQRepository {

    override fun getFAQ(): Flow<List<FrequentlyAskedQuestion>> {
        return flow {
            emit(
                faqApi.getFAQ().data?.searchQuestions()?.items()?.map { item ->
                    item.toDomain()
                } ?: persistentListOf()
            )
        }
    }
}
