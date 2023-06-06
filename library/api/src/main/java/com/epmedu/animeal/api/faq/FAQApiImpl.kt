package com.epmedu.animeal.api.faq

import com.amplifyframework.datastore.generated.model.Question
import com.epmedu.animeal.api.AnimealApi
import kotlinx.coroutines.flow.Flow

internal class FAQApiImpl(
    private val animealApi: AnimealApi
) : FAQApi {

    override fun getFAQ(): Flow<List<Question>> {
        return animealApi.getModelList(Question::class.java)
    }
}