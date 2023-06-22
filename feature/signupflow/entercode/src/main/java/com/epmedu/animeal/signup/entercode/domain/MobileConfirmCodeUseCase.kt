package com.epmedu.animeal.signup.entercode.domain

import com.epmedu.animeal.common.domain.wrapper.ActionResult

class MobileConfirmCodeUseCase(private val repository: EnterCodeRepository) {

    suspend operator fun invoke(
        code: List<Int?>
    ): ActionResult<Unit> {
        return repository.confirmSignIn(code)
    }
}