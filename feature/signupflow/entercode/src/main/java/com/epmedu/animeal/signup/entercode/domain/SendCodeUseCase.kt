package com.epmedu.animeal.signup.entercode.domain

import com.epmedu.animeal.common.domain.wrapper.ActionResult

class SendCodeUseCase(private val repository: EnterCodeRepository) {

    suspend operator fun invoke(): ActionResult<Any> {
        return repository.sendCode()
    }
}