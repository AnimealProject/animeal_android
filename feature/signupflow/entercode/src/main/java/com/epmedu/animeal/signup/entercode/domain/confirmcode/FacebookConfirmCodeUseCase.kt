package com.epmedu.animeal.signup.entercode.domain.confirmcode

import com.epmedu.animeal.auth.AuthRequestHandler
import com.epmedu.animeal.signup.entercode.data.EnterCodeRepository

class FacebookConfirmCodeUseCase(private val repository: EnterCodeRepository) : ConfirmCodeUseCase {

    override operator fun invoke(
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
