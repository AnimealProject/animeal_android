package com.epmedu.animeal.signup.entercode.domain

import com.epmedu.animeal.signup.entercode.data.EnterCodeRepository
import kotlinx.coroutines.flow.Flow

class GetPhoneNumberUseCase(private val repository: EnterCodeRepository) {
    operator fun invoke(): Flow<String> {
        return repository.phoneNumberWithPrefix
    }
}