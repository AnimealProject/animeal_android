package com.epmedu.animeal.profile.domain

import com.epmedu.animeal.auth.AuthRequestHandler
import com.epmedu.animeal.profile.data.repository.ProfileRepository

class LogOutUseCase(private val repository: ProfileRepository) {

    suspend operator fun invoke(
        onSuccess: () -> Unit,
        onError: (exception: Exception) -> Unit
    ) {
        val requestHandler = object : AuthRequestHandler {
            override fun onSuccess(result: Any?) {
                onSuccess()
            }

            override fun onError(exception: Exception) {
                onError(exception)
            }
        }

        repository.logOut(requestHandler)
    }
}