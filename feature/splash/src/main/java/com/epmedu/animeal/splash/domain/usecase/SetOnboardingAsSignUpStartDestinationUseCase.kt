package com.epmedu.animeal.splash.domain.usecase

import com.epmedu.animeal.router.domain.RouterRepository

class SetOnboardingAsSignUpStartDestinationUseCase(private val repository: RouterRepository) {
    operator fun invoke() = repository.setOnboardingAsSignUpStartDestination()
}