package com.epmedu.animeal.profile.domain

import com.epmedu.animeal.profile.domain.model.BasicProfile
import com.epmedu.animeal.profile.domain.repository.ProfileRepository

class SaveProfileUseCase(private val repository: ProfileRepository) {
    operator fun invoke(profile: BasicProfile) = repository.saveProfile(profile)
}