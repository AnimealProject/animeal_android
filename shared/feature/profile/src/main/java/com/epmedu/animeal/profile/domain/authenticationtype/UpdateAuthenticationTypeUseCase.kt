package com.epmedu.animeal.profile.domain.authenticationtype

import com.epmedu.animeal.navigation.route.AuthenticationType
import com.epmedu.animeal.profile.data.repository.AuthenticationTypeRepository

class UpdateAuthenticationTypeUseCase(private val repository: AuthenticationTypeRepository) {

    suspend operator fun invoke(authenticationType: AuthenticationType) =
        repository.updateAuthenticationType(authenticationType)
}