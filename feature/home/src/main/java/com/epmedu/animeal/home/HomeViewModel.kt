package com.epmedu.animeal.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.component.BuildConfigProvider
import com.epmedu.animeal.common.component.LocationProvider
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val buildConfigProvider: BuildConfigProvider,
    private val locationProvider: LocationProvider
) : ViewModel(),
    StateDelegate<HomeState> by DefaultStateDelegate(initialState = HomeState()) {

    init {
        updateState {
            copy(
                mapBoxPublicKey = buildConfigProvider.mapBoxPublicKey,
                mapBoxStyleUri = buildConfigProvider.mapBoxStyleURI
            )
        }

        viewModelScope.launch {
            locationProvider.fetchUpdates().collect {
                updateState { copy(currentLocation = it) }
            }
        }
    }
}