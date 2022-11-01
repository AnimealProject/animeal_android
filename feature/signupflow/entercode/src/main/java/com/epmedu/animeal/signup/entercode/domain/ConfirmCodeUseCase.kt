package com.epmedu.animeal.signup.entercode.domain

import com.epmedu.animeal.signup.entercode.data.EnterCodeRepository

class ConfirmCodeUseCase(private val repository: EnterCodeRepository) {
    operator fun invoke(
        code: List<Int?>,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) = repository.confirmCode(code, onSuccess, onError)
}