package com.epmedu.animeal.signup.entercode.domain.confirmcode

interface ConfirmCodeUseCase {

    operator fun invoke(
        code: List<Int?>,
        onSuccess: () -> Unit,
        onError: (exception: Exception) -> Unit,
    )
}

class InvalidCodeError : Exception()