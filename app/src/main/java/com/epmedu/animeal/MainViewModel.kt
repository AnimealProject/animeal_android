package com.epmedu.animeal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.presentation.viewmodel.delegate.ActionDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.handler.loading.LoadingHandler
import com.epmedu.animeal.networkuser.domain.usecase.LogOutUseCase
import com.epmedu.animeal.profile.domain.ClearProfileUseCase
import com.epmedu.animeal.profile.domain.repository.ProfileRepository
import com.epmedu.animeal.router.domain.RouterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    stateDelegate: StateDelegate<MainState>,
    actionDelegate: ActionDelegate,
    private val routerRepository: RouterRepository,
    private val logOutUseCase: LogOutUseCase,
    private val clearProfileUseCase: ClearProfileUseCase,
    private val loadingHandler: LoadingHandler,
    private val repository: ProfileRepository
) : ViewModel(),
    StateDelegate<MainState> by stateDelegate,
    ActionDelegate by actionDelegate {

    init {
        viewModelScope.launch {
            repository.getProfile().collect {
                // TODO check whether any valid user exists. User model needs to be reworked in aligning with guest role
                if (it.isFilled()) {
                    getRefreshTokenExpiration()
                }
            }
        }
    }

    fun confirmRefreshTokenExpirationWasHandled() {
        updateState { copy(navigateToStartDestination = false) }
        routerRepository.confirmRefreshTokenExpirationWasHandled()
    }

    private suspend fun getRefreshTokenExpiration() {
        routerRepository.getRefreshTokenExpirationFlow().collect {
            if (it) {
                handleRefreshTokenExpiration()
            }
        }
    }

    private suspend fun handleRefreshTokenExpiration() {
        performAction(
            action = { logOutUseCase() },
            onSuccess = {
                clearProfileUseCase()
                updateState { copy(navigateToStartDestination = true) }
                loadingHandler.hideLoading()
            }
        )
    }
}