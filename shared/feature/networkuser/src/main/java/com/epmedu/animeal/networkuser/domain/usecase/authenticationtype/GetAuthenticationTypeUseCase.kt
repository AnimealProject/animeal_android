package com.epmedu.animeal.networkuser.domain.usecase.authenticationtype

import com.epmedu.animeal.networkuser.domain.repository.AuthenticationTypeRepository

class GetAuthenticationTypeUseCase(private val repository: AuthenticationTypeRepository) {

    suspend operator fun invoke() = repository.getAuthenticationType()
}