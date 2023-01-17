package com.epmedu.animeal.signup.entercode.domain

import com.amplifyframework.auth.result.AuthSignInResult
import com.epmedu.animeal.auth.AuthRequestHandler
import com.epmedu.animeal.signup.entercode.data.EnterCodeRepository

class MobileConfirmCodeUseCase(private val repository: EnterCodeRepository) {

    operator fun invoke(
        code: List<Int?>,
        onSuccess: () -> Unit,
        onError: (exception: Exception) -> Unit,
    ) {
        val requestHandler = object : AuthRequestHandler {
            override fun onSuccess(result: Any?) {
                val authResult = result as AuthSignInResult
                when {
                    authResult.isSignInComplete -> onSuccess()
                    else -> onError(InvalidCodeError())
                }
            }

            override fun onError(exception: Exception) {
                onError(exception)
            }
        }

        repository.confirmSignIn(code, requestHandler)
    }
}

class InvalidCodeError : Exception()