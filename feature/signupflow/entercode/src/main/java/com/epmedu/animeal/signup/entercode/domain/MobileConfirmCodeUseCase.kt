package com.epmedu.animeal.signup.entercode.domain

import com.epmedu.animeal.common.data.wrapper.ApiResult

class MobileConfirmCodeUseCase(private val repository: EnterCodeRepository) {

    suspend operator fun invoke(
        code: List<Int?>,
        onSuccess: () -> Unit,
        onError: (exception: Exception) -> Unit,
    ) {
        when (val result = repository.confirmSignIn(code)) {
            is ApiResult.Success -> {
                val authResult = result.data
                when {
                    authResult.isSignedIn -> onSuccess()
                    else -> onError(InvalidCodeError())
                }
            }

            is ApiResult.Failure -> {
                onError(result.error as Exception)
            }
        }
    }
}

class InvalidCodeError : Exception()