package com.epmedu.animeal.signup.enterphone.domain

import com.epmedu.animeal.signup.enterphone.data.EnterPhoneRepository

class SignUpAndSignInUseCase(private val repository: EnterPhoneRepository) {
    operator fun invoke(
        phone: String,
        password: String,
        onSuccess: () -> Unit,
        onError: () -> Unit,
    ) = repository.signUpAndSignIn(phone, password, onSuccess, onError)
}