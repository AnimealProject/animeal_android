package com.epmedu.animeal.profile.domain

import com.epmedu.animeal.profile.data.repository.ProfileRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LogOutUseCase(private val repository: ProfileRepository) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    suspend operator fun invoke(
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        repository.logOut(
            onSuccess = { clearProfileAfterLogOut(onSuccess) },
            onError = onError,
        )
    }

    private fun clearProfileAfterLogOut(onSuccess: () -> Unit) {
        coroutineScope.launch {
            repository.clearProfile()
            onSuccess()
        }
    }
}