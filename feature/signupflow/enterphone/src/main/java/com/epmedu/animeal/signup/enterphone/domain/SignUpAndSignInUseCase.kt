package com.epmedu.animeal.signup.enterphone.domain

import com.amplifyframework.auth.AuthException.UsernameExistsException
import com.epmedu.animeal.auth.AuthRequestHandler
import com.epmedu.animeal.signup.enterphone.data.EnterPhoneRepository

class SignUpAndSignInUseCase(private val repository: EnterPhoneRepository) {
    operator fun invoke(
        phone: String,
        password: String,
        onSuccess: () -> Unit,
        onError: () -> Unit,
    ) {
        val requestHandler = object : AuthRequestHandler {
            override fun onSuccess(result: Any?) {
                signIn(phone, onSuccess, onError)
            }

            override fun onError(exception: Exception) {
                if (exception is UsernameExistsException) {
                    signIn(phone, onSuccess, onError)
                } else {
                    onError()
                }
            }
        }

        repository.signUp(phone, password, requestHandler)
    }

    private fun signIn(
        phone: String,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        val signInResult = object : AuthRequestHandler {
            override fun onSuccess(result: Any?) {
                onSuccess()
            }

            override fun onError(exception: Exception) {
                onError()
            }
        }

        repository.signIn(phone, signInResult)
    }
}