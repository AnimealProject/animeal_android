package com.epmedu.animeal.feeding.data.model

import com.amplifyframework.datastore.generated.model.Pet
import com.epmedu.animeal.extensions.toLocalDate
import com.epmedu.animeal.feeding.data.model.CaretakerData.Companion.toCaretakerData
import com.epmedu.animeal.feeding.data.model.PetDataLocalisation.Companion.toPetDataLocalisation
import java.time.LocalDate

data class PetData(
    val id: String? = null,
    val name: String? = null,
    val images: List<String>? = null,
    val breed: String? = null,
    val color: String? = null,
    val chipNumber: String? = null,
    val vaccinatedAt: LocalDate? = null,
    val yearOfBirth: LocalDate? = null,
    val caretaker: CaretakerData? = null,
    val localisations: List<PetDataLocalisation?>? = null,
    val createdAt: LocalDate? = null,
    val updatedAt: LocalDate? = null,
    val createdBy: String? = null,
    val updatedBy: String? = null,
    val owner: String? = null,
    val categoryTag: CategoryTagData? = null,
    val cover: String? = null,
    val petCategoryId: String? = null,
) {
    companion object {
        fun Pet.toPetData(): PetData = PetData(
            id = id,
            name = name,
            images = images,
            breed = breed,
            color = color,
            chipNumber = chipNumber,
            vaccinatedAt = vaccinatedAt?.toLocalDate(),
            yearOfBirth = yearOfBirth?.toLocalDate(),
            caretaker = caretaker?.toCaretakerData(),
            localisations = i18n?.map { it?.toPetDataLocalisation() },
            createdAt = createdAt?.toLocalDate(),
            updatedAt = updatedAt?.toLocalDate(),
            createdBy = createdBy,
            updatedBy = updatedBy,
            owner = owner,
            categoryTag = category?.tag?.toCategoryTagData(),
            cover = cover,
            petCategoryId = petCategoryId
        )
    }
}
