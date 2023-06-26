package com.epmedu.animeal.api.faq

import com.epmedu.animeal.api.AnimealApi
import com.epmedu.animeal.common.data.wrapper.ApiResult
import type.SearchableQuestionFilterInput

internal class FAQApiImpl(
    private val animealApi: AnimealApi
) : FAQApi {

    override suspend fun getFAQ(): ApiResult<SearchQuestionsQuery.Data> {
        val query = SearchQuestionsQuery.builder()
            .filter(
                SearchableQuestionFilterInput.builder()
                    .build()
            )
            .build()
        return animealApi.launchQuery(
            query = query,
            responseClass = SearchQuestionsQuery.Data::class.java
        )
    }
}