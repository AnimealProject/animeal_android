package com.epmedu.animeal.signup.enterphone.domain

import com.epmedu.animeal.profile.domain.model.Region
import com.epmedu.animeal.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.withContext

class SavePhoneNumberInfoUseCase(private val repository: ProfileRepository) {
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
            repository.updatePhoneAndRegion(phoneNumber, region)
            onSuccess()
        }
    }
}