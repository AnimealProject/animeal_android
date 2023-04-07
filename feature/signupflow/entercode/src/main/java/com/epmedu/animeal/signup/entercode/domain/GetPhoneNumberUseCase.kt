package com.epmedu.animeal.signup.entercode.domain

import kotlinx.coroutines.flow.Flow

class GetPhoneNumberUseCase(private val repository: EnterCodeRepository) {
    operator fun invoke(): Flow<String> {
        return repository.phoneNumberWithPrefix
    }
}