package com.epmedu.animeal.splash.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.splash.domain.usecase.GetIsSignedInUseCase
import com.epmedu.animeal.splash.presentation.viewmodel.SplashNextDestination.Home
import com.epmedu.animeal.splash.presentation.viewmodel.SplashNextDestination.Onboarding
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SplashViewModel @Inject constructor(
    private val getIsSignedInUseCase: GetIsSignedInUseCase
) : ViewModel(),
    StateDelegate<SplashState> by DefaultStateDelegate(SplashState()) {

    init {
        checkIfUserIsSignedIn()
    }

    private fun checkIfUserIsSignedIn() {
        viewModelScope.launch {
            navigateToNextDirection(
                when {
                    getIsSignedInUseCase() -> Home
                    else -> Onboarding
                }
            )
        }
    }

    private fun navigateToNextDirection(destination: SplashNextDestination) {
        updateState { copy(nextDestination = destination) }
    }
}