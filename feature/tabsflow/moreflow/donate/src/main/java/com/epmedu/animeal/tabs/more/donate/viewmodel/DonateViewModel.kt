package com.epmedu.animeal.tabs.more.donate.viewmodel

import androidx.lifecycle.ViewModel
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.tabs.more.donate.DonateScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class DonateViewModel :
    ViewModel(),
    StateDelegate<DonateState> by DefaultStateDelegate(initialState = DonateState()) {

    fun handleEvent(event: DonateScreenEvent) {
        when (event) {
            is DonateScreenEvent.DonateNumberClicked -> {
                updateState {
                    copy(donationNumberToCopy = event.number)
                }
            }
            is DonateScreenEvent.BackClicked -> {
                updateState {
                    copy(popBackstack = true)
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