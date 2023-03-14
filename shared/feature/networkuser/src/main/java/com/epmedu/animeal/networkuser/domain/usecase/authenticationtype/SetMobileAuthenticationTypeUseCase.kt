package com.epmedu.animeal.networkuser.domain.usecase.authenticationtype

import com.epmedu.animeal.networkuser.domain.repository.AuthenticationTypeRepository

class SetMobileAuthenticationTypeUseCase(private val repository: AuthenticationTypeRepository) {

    operator fun invoke() = repository.setAuthenticationTypeAsMobile()
}