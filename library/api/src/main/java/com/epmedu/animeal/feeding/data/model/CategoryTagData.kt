package com.epmedu.animeal.feeding.data.model

import com.amplifyframework.datastore.generated.model.CategoryTag
import com.epmedu.animeal.feeding.data.model.CategoryTagData.Cats
import com.epmedu.animeal.feeding.data.model.CategoryTagData.Dogs

enum class CategoryTagData {
    Dogs, Cats
}

internal fun CategoryTag.toCategoryTagData() = when (this) {
    CategoryTag.cats -> Cats
    CategoryTag.dogs -> Dogs
}