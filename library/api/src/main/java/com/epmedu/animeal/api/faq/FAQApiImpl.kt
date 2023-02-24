package com.epmedu.animeal.api.faq

import com.amplifyframework.datastore.generated.model.Question
import com.epmedu.animeal.api.extensions.getModelList
import kotlinx.coroutines.flow.Flow

internal class FAQApiImpl : FAQApi {

    override fun getFAQ(): Flow<List<Question>> {
        return getModelList()
    }
}