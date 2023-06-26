package com.epmedu.animeal.api.faq

import com.epmedu.animeal.common.data.wrapper.ApiResult

interface FAQApi {

    suspend fun getFAQ(): ApiResult<SearchQuestionsQuery.Data>
}
