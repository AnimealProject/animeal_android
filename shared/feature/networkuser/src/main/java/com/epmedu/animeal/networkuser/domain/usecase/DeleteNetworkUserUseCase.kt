package com.epmedu.animeal.networkuser.domain.usecase

import com.epmedu.animeal.networkuser.domain.repository.NetworkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteNetworkUserUseCase(private val repository: NetworkRepository) {

    suspend operator fun invoke(
        onSuccess: () -> Unit,
        onError: (exception: Throwable) -> Unit
    ) {
        withContext(Dispatchers.IO) {
            repository.deleteNetworkUser(onSuccess, onError)
        }
    }
}