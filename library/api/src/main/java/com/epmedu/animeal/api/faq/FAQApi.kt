package com.epmedu.animeal.api.faq

import com.amplifyframework.datastore.generated.model.Question
import kotlinx.coroutines.flow.Flow

interface FAQApi {

    fun getFAQ(): Flow<List<Question>>
}