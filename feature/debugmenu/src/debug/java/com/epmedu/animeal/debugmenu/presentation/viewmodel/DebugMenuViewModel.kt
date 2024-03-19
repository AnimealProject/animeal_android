package com.epmedu.animeal.debugmenu.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.debugmenu.domain.DebugMenuRepository
import com.epmedu.animeal.debugmenu.presentation.DebugMenuScreenEvent
import com.epmedu.animeal.debugmenu.presentation.DebugMenuScreenEvent.ResetGeolocationPermissionRequestedAgain
import com.epmedu.animeal.debugmenu.presentation.DebugMenuScreenEvent.SetFinishProfileAsStartDestination
import com.epmedu.animeal.debugmenu.presentation.DebugMenuScreenEvent.SwitchUsingMockedFeedingPoints
import com.epmedu.animeal.permissions.domain.UpdateAppSettingsUseCase
import com.epmedu.animeal.router.domain.RouterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
internal class DebugMenuViewModel @Inject constructor(
    private val updateAppSettingsUseCase: UpdateAppSettingsUseCase,
    private val routerRepository: RouterRepository,
    private val debugMenuRepository: DebugMenuRepository
) : ViewModel() {

    fun handleEvents(event: DebugMenuScreenEvent) {
        when (event) {
            is SwitchUsingMockedFeedingPoints -> {
                debugMenuRepository.useMockedFeedingPoints = event.useMockedFeedingPoint
            }

            is SetFinishProfileAsStartDestination -> {
                routerRepository.setFinishProfileAsSignUpStartDestination()
            }

            is ResetGeolocationPermissionRequestedAgain -> {
                viewModelScope.launch {
                    updateAppSettingsUseCase { isGeolocationPermissionRationaleShown = false }
                }
            }
        }
    }
}