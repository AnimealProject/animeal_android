package com.epmedu.animeal.networkuser.domain.usecase.authenticationtype

import com.epmedu.animeal.networkuser.domain.repository.AuthenticationTypeRepository

class SetFacebookAuthenticationTypeUseCase(private val repository: AuthenticationTypeRepository) {

    suspend operator fun invoke(isPhoneNumberVerified: Boolean) =
        repository.setAuthenticationTypeAsFacebook(isPhoneNumberVerified)
}