package com.epmedu.animeal.signup.enterphone.domain

import com.epmedu.animeal.profile.domain.model.Region
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.withContext

class SavePhoneNumberInfoUseCase(private val repository: EnterPhoneRepository) {
    suspend operator fun invoke(
        region: Region,
        phoneNumber: String,
        onSuccess: () -> Unit,
        onError: () -> Unit,
    ) {
        withContext(
            CoroutineExceptionHandler { _, _ ->
                onError()
            }
        ) {
            repository.savePhoneNumberAndRegion(region, phoneNumber)
            onSuccess()
        }
    }
}