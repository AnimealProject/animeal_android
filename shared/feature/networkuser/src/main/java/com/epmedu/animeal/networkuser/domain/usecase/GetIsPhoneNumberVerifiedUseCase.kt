package com.epmedu.animeal.networkuser.domain.usecase

import com.epmedu.animeal.networkuser.domain.repository.NetworkRepository

class GetIsPhoneNumberVerifiedUseCase(private val repository: NetworkRepository) {

    suspend operator fun invoke() = repository.isPhoneNumberVerified()
}