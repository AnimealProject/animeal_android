package com.epmedu.animeal.signup.enterphone.domain

import com.amplifyframework.auth.cognito.exceptions.service.UsernameExistsException
import com.amplifyframework.auth.exceptions.InvalidStateException
import com.epmedu.animeal.common.domain.wrapper.ActionResult

class SignUpAndSignInUseCase(private val repository: EnterPhoneRepository) {
    suspend operator fun invoke(
        phone: String,
        password: String,
    ): ActionResult<Unit> {
        return when (val result = repository.signUp(phone, password)) {
            is ActionResult.Success -> {
                signIn(phone)
            }

            is ActionResult.Failure -> {
                when (result.error) {
                    is UsernameExistsException, is InvalidStateException -> {
                        signIn(phone)
                    }
                    else -> {
                        ActionResult.Failure(result.error)
                    }
                }
            }
        }
    }

    private suspend fun signIn(
        phone: String,
    ): ActionResult<Unit> {
        return repository.signIn(phone)
    }
}