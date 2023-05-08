package com.epmedu.animeal.signup.entercode.domain

import com.epmedu.animeal.auth.AuthRequestHandler

class FacebookConfirmCodeUseCase(private val repository: EnterCodeRepository) {

    operator fun invoke(
        code: List<Int?>,
        onSuccess: () -> Unit,
        onError: (exception: Exception) -> Unit,
    ) {
        val requestHandler = object : AuthRequestHandler {
            override fun onSuccess(result: Any?) {
                onSuccess()
            }

            override fun onError(exception: Exception) {
                onError(exception)
            }
        }

        repository.confirmResendCode(code, requestHandler)
    }
}
