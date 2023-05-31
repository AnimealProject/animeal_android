package com.epmedu.animeal.signup.enterphone.domain

import com.amplifyframework.auth.cognito.exceptions.service.UsernameExistsException
import com.epmedu.animeal.common.data.wrapper.ApiResult

class SignUpAndSignInUseCase(private val repository: EnterPhoneRepository) {
    suspend operator fun invoke(
        phone: String,
        password: String,
        onSuccess: () -> Unit,
        onError: () -> Unit,
    ) {
        when (val result = repository.signUp(phone, password)) {
            is ApiResult.Success -> {
                signIn(phone, onSuccess, onError)
            }
            is ApiResult.Failure -> {
                if (result.error is UsernameExistsException) {
                    signIn(phone, onSuccess, onError)
                } else {
                    onError()
                }
            }
        }
    }

    private suspend fun signIn(
        phone: String,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        when (repository.signIn(phone)) {
            is ApiResult.Success -> {
                onSuccess()
            }
            is ApiResult.Failure -> {
                onError()
            }
        }
    }
}