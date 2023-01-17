package com.epmedu.animeal.networkuser.domain.authenticationtype

import com.epmedu.animeal.networkuser.data.repository.AuthenticationTypeRepository

class SetMobileAuthenticationTypeUseCase(private val repository: AuthenticationTypeRepository) {

    suspend operator fun invoke() = repository.setAuthenticationTypeAsMobile()
}