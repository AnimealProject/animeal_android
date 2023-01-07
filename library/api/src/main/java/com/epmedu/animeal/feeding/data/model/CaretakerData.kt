package com.epmedu.animeal.feeding.data.model

import com.amplifyframework.datastore.generated.model.Caretaker

data class CaretakerData(
    val fullName: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null
) {
    internal companion object {
        fun Caretaker.toCaretakerData(): CaretakerData = CaretakerData(
            fullName = fullName,
            email = email,
            phoneNumber = phoneNumber
        )
    }
}
