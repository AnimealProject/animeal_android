package com.epmedu.animeal.splash.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.data.wrapper.ApiResult
import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.networkuser.domain.usecase.GetIsPhoneNumberVerifiedUseCase
import com.epmedu.animeal.networkuser.domain.usecase.authenticationtype.SetFacebookAuthenticationTypeUseCase
import com.epmedu.animeal.networkuser.domain.usecase.authenticationtype.SetMobileAuthenticationTypeUseCase
import com.epmedu.animeal.profile.domain.LogOutUseCase
import com.epmedu.animeal.splash.domain.usecase.GetIsProfileSavedUseCase
import com.epmedu.animeal.splash.domain.usecase.GetIsSignedInUseCase
import com.epmedu.animeal.splash.domain.usecase.SetFinishProfileAsStartDestinationUseCase
import com.epmedu.animeal.splash.presentation.SplashScreenEvent
import com.epmedu.animeal.splash.presentation.SplashScreenEvent.ErrorShown
import com.epmedu.animeal.splash.presentation.viewmodel.SplashNextDestination.Home
import com.epmedu.animeal.splash.presentation.viewmodel.SplashNextDestination.SignUp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SplashViewModel @Inject constructor(
    private val getIsSignedInUseCase: GetIsSignedInUseCase,
    private val getIsProfileSavedUseCase: GetIsProfileSavedUseCase,
    private val getIsPhoneNumberVerifiedUseCase: GetIsPhoneNumberVerifiedUseCase,
    private val setMobileAuthenticationTypeUseCase: SetMobileAuthenticationTypeUseCase,
    private val setFacebookAuthenticationTypeUseCase: SetFacebookAuthenticationTypeUseCase,
    private val setFinishProfileAsStartDestinationUseCase: SetFinishProfileAsStartDestinationUseCase,
    private val logOutUseCase: LogOutUseCase
) : ViewModel(),
    StateDelegate<SplashState> by DefaultStateDelegate(SplashState()) {

    init {
        checkIfUserIsSignedIn()
    }

    private fun checkIfUserIsSignedIn() {
        viewModelScope.launch {
            if (getIsSignedInUseCase()) {
                val isPhoneNumberVerified = async { getIsPhoneNumberVerifiedUseCase() }
                val isProfileSaved = async { getIsProfileSavedUseCase() }
                selectNextDirection(isPhoneNumberVerified.await(), isProfileSaved.await())
            } else {
                navigateToNextDirection(SignUp)
            }
        }
    }

    private suspend fun selectNextDirection(
        isPhoneNumberVerifiedResult: ActionResult<Boolean>,
        isProfileSaved: Boolean
    ) {
        if (isPhoneNumberVerifiedResult is ActionResult.Success) {
            val isPhoneNumberVerified = isPhoneNumberVerifiedResult.result
            when {
                isPhoneNumberVerified && isProfileSaved -> {
                    navigateToNextDirection(Home)
                }
                isPhoneNumberVerified -> {
                    setMobileAuthenticationTypeUseCase()
                    setFinishProfileAsStartDestinationUseCase()
                    navigateToNextDirection(SignUp)
                }
                isProfileSaved -> {
                    setFacebookAuthenticationTypeUseCase(isPhoneNumberVerified = false)
                    setFinishProfileAsStartDestinationUseCase()
                    navigateToNextDirection(SignUp)
                }
                else -> {
                    logOut()
                }
            }
        } else {
            logOut()
        }
    }

    private suspend fun logOut() {
        when(val result = logOutUseCase()) {
            is ActionResult.Success -> {
                navigateToNextDirection(SignUp)
            }
            is ActionResult.Failure -> {
                updateState { copy(isError = true) }
            }
        }
    }

    private fun navigateToNextDirection(destination: SplashNextDestination) {
        updateState { copy(nextDestination = destination) }
    }

    fun handleEvents(event: SplashScreenEvent) {
        when (event) {
            is ErrorShown -> updateState { copy(isError = false) }
        }
    }
}