package com.epmedu.animeal.splash.domain.usecase

import com.epmedu.animeal.profile.data.repository.ProfileRepository

class GetIsProfileSavedUseCase(private val repository: ProfileRepository) {

    suspend operator fun invoke() = repository.isProfileSaved()
}