package com.epmedu.animeal.signup.enterphone.domain

import com.amplifyframework.auth.cognito.exceptions.service.UserNotFoundException
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
                    is UsernameExistsException -> {
                        signIn(phone)
                    }
                    // this is thrown when the user is already signed in
                    is InvalidStateException -> {
                        handleInvalidStateException(phone, password)
                    }
                    else -> {
                        result
                    }
                }
            }
        }
    }

    private suspend fun signIn(phone: String): ActionResult<Unit> {
        return repository.signIn(phone)
    }

    private suspend fun handleInvalidStateException(
        phone: String,
        password: String
    ): ActionResult<Unit> {
        // We can't sign out the user here because the OTP is not confirmed yet.
        // So the only way to handle this is to try to sign in the user.
        val result = signIn(phone)
        return when {
            result is ActionResult.Failure && result.error is UserNotFoundException -> {
                invoke(phone, password)
            }
            else -> {
                result
            }
        }
    }
}