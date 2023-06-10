package com.epmedu.animeal.profile.domain

import com.epmedu.animeal.common.data.wrapper.ApiResult
import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.profile.domain.repository.ProfileRepository
import com.epmedu.animeal.router.domain.RouterRepository

class LogOutUseCase(
    private val profileRepository: ProfileRepository,
    private val routerRepository: RouterRepository
) {

    suspend operator fun invoke() : ActionResult<Unit> {
        profileRepository.logOut()
        routerRepository.setOnboardingAsSignUpStartDestination()
        return ActionResult.Success(Unit)
    }
}