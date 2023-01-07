package com.epmedu.animeal.feeding.data.model

import com.amplifyframework.datastore.generated.model.FeedingPointI18n

data class FeedingPointDataLocalisation(
    val locale: String? = null,
    val name: String? = null,
    val description: String? = null,
    val city: String? = null,
    val street: String? = null,
    val address: String? = null,
    val region: String? = null,
    val neighborhood: String? = null,
) {
    companion object {
        fun FeedingPointI18n.toFeedingPointDataLocalisation(): FeedingPointDataLocalisation =
            FeedingPointDataLocalisation(
                locale = locale,
                name = name,
                description = description,
                city = city,
                street = street,
                address = address,
                region = region,
                neighborhood = neighborhood
            )
    }
}
