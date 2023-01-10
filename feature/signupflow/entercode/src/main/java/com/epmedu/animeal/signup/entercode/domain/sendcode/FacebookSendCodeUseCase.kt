package com.epmedu.animeal.signup.entercode.domain.sendcode

import com.epmedu.animeal.auth.AuthRequestHandler
import com.epmedu.animeal.signup.entercode.data.EnterCodeRepository
import java.lang.Exception

class FacebookSendCodeUseCase(private val repository: EnterCodeRepository) : SendCodeUseCase {
    override suspend operator fun invoke(
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        val handler = object : AuthRequestHandler {
            override fun onSuccess(result: Any?) {
                onSuccess()
            }

            override fun onError(exception: Exception) {
                onError()
            }
        }
        repository.sendCodeByResend(handler)
    }
}