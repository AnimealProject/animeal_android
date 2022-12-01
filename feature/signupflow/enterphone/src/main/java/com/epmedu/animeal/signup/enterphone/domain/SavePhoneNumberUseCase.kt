package com.epmedu.animeal.signup.enterphone.domain

import com.epmedu.animeal.signup.enterphone.data.EnterPhoneRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.withContext

class SavePhoneNumberUseCase(private val repository: EnterPhoneRepository) {
    suspend operator fun invoke(
        phoneNumber: String,
        onSuccess: () -> Unit,
        onError: () -> Unit,
    ) {
        withContext(
            CoroutineExceptionHandler { _, _ ->
                onError()
            }
        ) {
            repository.savePhoneNumber(phoneNumber)
            onSuccess()
        }
    }
}