package com.epmedu.animeal.profile.domain

import com.epmedu.animeal.auth.AuthRequestHandler
import com.epmedu.animeal.profile.data.repository.ProfileRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LogOutUseCase(private val repository: ProfileRepository) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    suspend operator fun invoke(
        onSuccess: () -> Unit,
        onError: (exception: Exception) -> Unit
    ) {
        val requestHandler = object : AuthRequestHandler {
            override fun onSuccess(result: Any?) {
                clearProfileAfterLogOut(onSuccess)
            }

            override fun onError(exception: Exception) {
                onError(exception)
            }
        }

        repository.logOut(requestHandler)
    }

    private fun clearProfileAfterLogOut(onSuccess: () -> Unit) {
        coroutineScope.launch {
            repository.clearProfile()
            onSuccess()
        }
    }
}