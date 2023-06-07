package com.epmedu.animeal.signup.entercode.domain

import com.epmedu.animeal.common.domain.wrapper.ActionResultData

class MobileConfirmCodeUseCase(private val repository: EnterCodeRepository) {

    suspend operator fun invoke(
        code: List<Int?>,
        onSuccess: () -> Unit,
        onError: (exception: Exception) -> Unit,
    ) {
        when (val result = repository.confirmSignIn(code)) {
            is ActionResultData.Success -> {
                val authResult = result.data
                when {
                    authResult.isSignedIn -> onSuccess()
                    else -> onError(InvalidCodeError())
                }
            }

            is ActionResultData.Failure -> {
                onError(result.error as Exception)
            }
        }
    }
}

class InvalidCodeError : Exception()