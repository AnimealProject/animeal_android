package com.epmedu.animeal.signup.entercode.domain

import com.epmedu.animeal.signup.entercode.data.EnterCodeRepository

class SendCodeUseCase(private val repository: EnterCodeRepository) {
    operator fun invoke(
        phoneNumber: String,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) = repository.sendCode(phoneNumber, onSuccess, onError)
}