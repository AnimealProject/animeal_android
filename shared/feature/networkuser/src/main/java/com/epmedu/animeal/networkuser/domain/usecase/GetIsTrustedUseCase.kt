package com.epmedu.animeal.networkuser.domain.usecase

import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.networkuser.domain.repository.NetworkRepository

class GetIsTrustedUseCase(private val networkRepository: NetworkRepository) {

    suspend operator fun invoke(): ActionResult<Boolean> {
        return networkRepository.isTrusted()
    }
}