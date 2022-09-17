package com.epmedu.animeal.profile.domain

import com.epmedu.animeal.profile.data.model.Profile
import com.epmedu.animeal.profile.data.repository.ProfileRepository

class SaveProfileUseCase(private val repository: ProfileRepository) {
    suspend fun execute(profile: Profile) = repository.saveProfile(profile)
}