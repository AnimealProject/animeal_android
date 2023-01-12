package com.epmedu.animeal.profile.domain.authenticationtype

import com.epmedu.animeal.profile.data.repository.AuthenticationTypeRepository

class GetAuthenticationTypeUseCase(private val repository: AuthenticationTypeRepository) {

    suspend operator fun invoke() = repository.getAuthenticationType()
}