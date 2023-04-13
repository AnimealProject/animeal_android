package com.epmedu.animeal.profile.domain

import com.epmedu.animeal.profile.data.model.Profile
import com.epmedu.animeal.profile.domain.repository.ProfileRepository

class SaveProfileUseCase(private val repository: ProfileRepository) {
    operator fun invoke(profile: Profile) = repository.saveProfile(profile)
}