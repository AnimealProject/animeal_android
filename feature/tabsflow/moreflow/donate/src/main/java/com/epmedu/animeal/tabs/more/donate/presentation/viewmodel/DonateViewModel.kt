package com.epmedu.animeal.tabs.more.donate.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.tabs.more.donate.domain.usecase.GetDonateInformationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DonateViewModel @Inject constructor(
    private val getDonateInformationUseCase: GetDonateInformationUseCase
) :
    ViewModel(),
    StateDelegate<DonateState> by DefaultStateDelegate(initialState = DonateState()) {

    init {
        viewModelScope.launch {
            getDonateInformationUseCase().collect {
                updateState { copy(donationInformation = it) }
            }
        }
    }
}