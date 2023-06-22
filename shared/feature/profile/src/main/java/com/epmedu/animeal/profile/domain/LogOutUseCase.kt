package com.epmedu.animeal.profile.domain

import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.profile.domain.repository.ProfileRepository
import com.epmedu.animeal.router.domain.RouterRepository

class LogOutUseCase(
    private val profileRepository: ProfileRepository,
    private val routerRepository: RouterRepository
) {
    suspend operator fun invoke(): ActionResult<Unit> {
        val result = profileRepository.logOut()
        when (result) {
            is ActionResult.Success -> {
                routerRepository.setOnboardingAsSignUpStartDestination()
            }

            is ActionResult.Failure -> {}
        }
        return result
    }
}