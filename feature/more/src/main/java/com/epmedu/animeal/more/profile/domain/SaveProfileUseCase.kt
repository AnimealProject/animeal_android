package com.epmedu.animeal.more.profile.domain

import com.epmedu.animeal.common.data.model.Profile
import com.epmedu.animeal.common.data.repository.ProfileRepository

internal class SaveProfileUseCase(private val repository: ProfileRepository) {
    suspend fun execute(profile: Profile) = repository.saveProfile(profile)
}