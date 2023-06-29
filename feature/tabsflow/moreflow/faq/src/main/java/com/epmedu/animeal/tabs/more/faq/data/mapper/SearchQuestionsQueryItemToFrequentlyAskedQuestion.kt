package com.epmedu.animeal.tabs.more.faq.data.mapper
import com.epmedu.animeal.tabs.more.faq.domain.model.FrequentlyAskedQuestion
internal fun SearchQuestionsQuery.Item.toDomain() = FrequentlyAskedQuestion(
    id = id(),
    question = value().orEmpty(),
    answer = answer().orEmpty()
)