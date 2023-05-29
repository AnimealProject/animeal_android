package com.epmedu.animeal.profile.domain

import com.epmedu.animeal.auth.AuthRequestHandler
import com.epmedu.animeal.profile.domain.repository.ProfileRepository
import com.epmedu.animeal.router.domain.RouterRepository

class LogOutUseCase(
    private val profileRepository: ProfileRepository,
    private val routerRepository: RouterRepository
) {

    suspend operator fun invoke(
        onSuccess: () -> Unit,
    ) {
        profileRepository.logOut {
            routerRepository.setOnboardingAsSignUpStartDestination()
            onSuccess()
        }
    }
}