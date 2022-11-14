package com.epmedu.animeal.signup.enterphone.domain

import com.epmedu.animeal.signup.enterphone.data.EnterPhoneRepository

class SavePhoneNumberUseCase(private val repository: EnterPhoneRepository) {
    suspend operator fun invoke(
        phoneNumber: String,
        onSuccess: () -> Unit,
        onError: () -> Unit,
    ) {
        repository.savePhoneNumber(phoneNumber)
        if (repository.isPhoneNumberSaved()) {
            onSuccess()
        } else {
            onError()
        }
    }
}