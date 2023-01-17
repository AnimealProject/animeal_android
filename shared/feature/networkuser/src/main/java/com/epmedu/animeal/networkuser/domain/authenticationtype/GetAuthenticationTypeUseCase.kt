package com.epmedu.animeal.networkuser.domain.authenticationtype

import com.epmedu.animeal.networkuser.data.repository.AuthenticationTypeRepository

class GetAuthenticationTypeUseCase(private val repository: AuthenticationTypeRepository) {

    suspend operator fun invoke() = repository.getAuthenticationType()
}