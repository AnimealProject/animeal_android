package com.epmedu.animeal.profile.domain

import com.epmedu.animeal.profile.domain.repository.ProfileRepository

class GetProfileUseCase(private val repository: ProfileRepository) {
    operator fun invoke() = repository.getProfile()
}