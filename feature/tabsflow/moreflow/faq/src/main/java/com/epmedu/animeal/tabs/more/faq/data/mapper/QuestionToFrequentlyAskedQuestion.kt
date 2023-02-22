package com.epmedu.animeal.tabs.more.faq.data.mapper

import com.amplifyframework.datastore.generated.model.Question
import com.epmedu.animeal.tabs.more.faq.domain.model.FrequentlyAskedQuestion

internal fun Question.toDomain() = FrequentlyAskedQuestion(
    id = id,
    question = value,
    answer = answer
)