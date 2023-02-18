package com.epmedu.animeal.splash.domain.usecase

import com.epmedu.animeal.splash.domain.repository.SplashRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetIsSignedInUseCase(private val repository: SplashRepository) {

    suspend operator fun invoke() = withContext(Dispatchers.IO) { repository.isSignedIn() }
}