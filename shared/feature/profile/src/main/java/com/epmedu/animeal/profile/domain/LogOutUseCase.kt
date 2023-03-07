package com.epmedu.animeal.profile.domain

import com.epmedu.animeal.auth.AuthRequestHandler
import com.epmedu.animeal.profile.data.repository.ProfileRepository
import com.epmedu.animeal.router.domain.RouterRepository

class LogOutUseCase(
    private val profileRepository: ProfileRepository,
    private val routerRepository: RouterRepository
) {

    suspend operator fun invoke(
        onSuccess: () -> Unit,
        onError: (exception: Exception) -> Unit
    ) {
        val requestHandler = object : AuthRequestHandler {
            override fun onSuccess(result: Any?) {
                routerRepository.setOnboardingAsSignUpStartDestination()
                onSuccess()
            }

            override fun onError(exception: Exception) {
                onError(exception)
            }
        }

        profileRepository.logOut(requestHandler)
    }
}