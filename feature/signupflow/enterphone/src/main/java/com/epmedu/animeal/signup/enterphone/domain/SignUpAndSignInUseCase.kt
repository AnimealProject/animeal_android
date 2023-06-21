package com.epmedu.animeal.signup.enterphone.domain

import com.amplifyframework.auth.cognito.exceptions.service.UsernameExistsException
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
                if (result.error is UsernameExistsException) {
                    signIn(phone)
                } else {
                    ActionResult.Failure(result.error)
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