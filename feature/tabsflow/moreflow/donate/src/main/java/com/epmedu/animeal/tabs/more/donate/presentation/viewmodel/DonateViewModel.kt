package com.epmedu.animeal.tabs.more.donate.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.tabs.more.donate.domain.usecase.GetDonateInformationUseCase
import com.epmedu.animeal.tabs.more.donate.presentation.DonateScreenEvent
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

    fun handleEvent(event: DonateScreenEvent) {
        when (event) {
            is DonateScreenEvent.DonateNumberClicked -> {
                updateState {
                    copy(donationNumberToCopy = event.number)
                }
            }

            is DonateScreenEvent.NumberIsCopied -> {
                updateState {
                    copy(donationNumberToCopy = null)
                }
            }
        }
    }
}