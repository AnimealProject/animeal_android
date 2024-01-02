package com.epmedu.animeal.networkuser.domain.usecase

import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.networkuser.domain.repository.NetworkRepository
import com.epmedu.animeal.router.domain.RouterRepository

class LogOutUseCase(
    private val networkRepository: NetworkRepository,
    private val routerRepository: RouterRepository,
) {
    suspend operator fun invoke(): ActionResult<Unit> {
        val result = networkRepository.logOut()
        when (result) {
            is ActionResult.Success -> {
                routerRepository.setOnboardingAsSignUpStartDestination()
            }

            is ActionResult.Failure -> {}
        }
        return result
    }
}