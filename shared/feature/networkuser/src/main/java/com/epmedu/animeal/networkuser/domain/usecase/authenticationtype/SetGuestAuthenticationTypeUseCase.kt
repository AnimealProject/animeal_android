package com.epmedu.animeal.networkuser.domain.usecase.authenticationtype

import com.epmedu.animeal.networkuser.domain.repository.AuthenticationTypeRepository

class SetGuestAuthenticationTypeUseCase(private val repository: AuthenticationTypeRepository) {

    operator fun invoke() = repository.setAuthenticationTypeAsGuest()
}