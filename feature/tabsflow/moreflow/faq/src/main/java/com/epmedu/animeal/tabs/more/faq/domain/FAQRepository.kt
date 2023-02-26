package com.epmedu.animeal.tabs.more.faq.domain

import com.epmedu.animeal.tabs.more.faq.domain.model.FrequentlyAskedQuestion
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

internal interface FAQRepository {
    fun getFAQ(): Flow<ImmutableList<FrequentlyAskedQuestion>>
}