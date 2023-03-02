package com.epmedu.animeal.networkuser.domain.usecase

import com.epmedu.animeal.networkuser.domain.repository.NetworkRepository

class DeleteNetworkUserUseCase(private val repository: NetworkRepository) {

    suspend operator fun invoke() = repository.deleteNetworkUser()
}