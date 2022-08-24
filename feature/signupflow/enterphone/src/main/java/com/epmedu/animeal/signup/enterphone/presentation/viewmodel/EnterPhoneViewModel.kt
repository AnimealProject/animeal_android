package com.epmedu.animeal.signup.enterphone.presentation.viewmodel

import android.telephony.PhoneNumberUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.signup.enterphone.data.EnterPhoneRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class EnterPhoneViewModel @Inject constructor(
    private val repository: EnterPhoneRepository
) : ViewModel(),
    StateDelegate<EnterPhoneState> by DefaultStateDelegate(initialState = EnterPhoneState()) {

    fun updatePhoneNumber(newNumber: String) {
        updateState {
            copy(
                phoneNumber = newNumber,
                isNextEnabled = newNumber.isValidPhoneNumber()
            )
        }
    }

    private fun String.isValidPhoneNumber(): Boolean {
        return length == PHONE_NUMBER_LENGTH && PhoneNumberUtils.isGlobalPhoneNumber(this)
    }

    fun savePhoneNumberAndSendCode() {
        viewModelScope.launch {
            repository.savePhoneNumberAndSendCode(state.phoneNumber)
        }
    }

    companion object {
        private const val PHONE_NUMBER_LENGTH = 9
    }
}