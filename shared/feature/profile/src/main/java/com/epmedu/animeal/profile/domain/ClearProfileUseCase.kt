package com.epmedu.animeal.profile.domain

import com.epmedu.animeal.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClearProfileUseCase(private val repository: ProfileRepository) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    operator fun invoke() {
        coroutineScope.launch {
            repository.clearProfile()
        }
    }
}