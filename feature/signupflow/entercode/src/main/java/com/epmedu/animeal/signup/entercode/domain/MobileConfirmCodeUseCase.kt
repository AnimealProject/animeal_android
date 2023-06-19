package com.epmedu.animeal.signup.entercode.domain

import com.amplifyframework.auth.result.AuthSignInResult
import com.epmedu.animeal.common.domain.wrapper.ActionResult

class MobileConfirmCodeUseCase(private val repository: EnterCodeRepository) {

    suspend operator fun invoke(
        code: List<Int?>
    ): ActionResult<AuthSignInResult> {
        return repository.confirmSignIn(code)
    }
}