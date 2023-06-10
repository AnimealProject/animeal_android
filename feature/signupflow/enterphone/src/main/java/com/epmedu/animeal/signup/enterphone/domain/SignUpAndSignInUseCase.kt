package com.epmedu.animeal.signup.enterphone.domain

import com.amplifyframework.auth.cognito.exceptions.service.UsernameExistsException
import com.epmedu.animeal.common.data.wrapper.ApiResult
import com.epmedu.animeal.common.domain.wrapper.ActionResult

class SignUpAndSignInUseCase(private val repository: EnterPhoneRepository) {
    suspend operator fun invoke(
        phone: String,
        password: String,
        onSuccess: () -> Unit,
        onError: () -> Unit,
    ) {
        when (val result = repository.signUp(phone, password)) {
            is ActionResult.Success -> {
                signIn(phone, onSuccess, onError)
            }
            is ActionResult.Failure -> {
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
            is ActionResult.Success -> {
                onSuccess()
            }
            is ActionResult.Failure -> {
                onError()
            }
        }
    }
}