package com.epmedu.animeal.profile.domain

import com.epmedu.animeal.profile.data.repository.ProfileRepository

class LogOutUseCase(private val repository: ProfileRepository) {
    operator fun invoke(
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) = repository.logOut(onSuccess, onError)
}