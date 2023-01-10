package com.epmedu.animeal.signup.entercode.domain.sendcode

interface SendCodeUseCase {

    suspend operator fun invoke(
        onSuccess: () -> Unit,
        onError: () -> Unit
    )
}