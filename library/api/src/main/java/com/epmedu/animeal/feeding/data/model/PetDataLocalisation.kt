package com.epmedu.animeal.feeding.data.model

import com.amplifyframework.datastore.generated.model.PetI18n

data class PetDataLocalisation(
    val locale: String? = null,
    val name: String? = null,
    val breed: String? = null,
    val color: String? = null,
) {
    companion object {
        fun PetI18n.toPetDataLocalisation(): PetDataLocalisation =
            PetDataLocalisation(locale, name, breed, color)
    }
}
