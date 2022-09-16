package com.epmedu.animeal.more.profile.domain

import com.epmedu.animeal.common.data.repository.ProfileRepository

internal class GetProfileUseCase(private val repository: ProfileRepository) {
    suspend fun execute() = repository.getProfile()
}