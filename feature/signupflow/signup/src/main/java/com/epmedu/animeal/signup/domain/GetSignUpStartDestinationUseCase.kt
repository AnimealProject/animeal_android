package com.epmedu.animeal.signup.domain

import com.epmedu.animeal.router.domain.RouterRepository

class GetSignUpStartDestinationUseCase(private val repository: RouterRepository) {

    operator fun invoke() = repository.getSignUpStartDestination()
}