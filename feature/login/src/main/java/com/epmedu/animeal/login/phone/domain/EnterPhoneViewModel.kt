package com.epmedu.animeal.login.phone.domain

import android.telephony.PhoneNumberUtils
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.domain.StateViewModel
import com.epmedu.animeal.login.phone.data.EnterPhoneRepository
import com.epmedu.animeal.login.phone.domain.model.EnterPhoneState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class EnterPhoneViewModel @Inject constructor(
    private val repository: EnterPhoneRepository
) : StateViewModel<EnterPhoneState>(initialState = EnterPhoneState()) {

    fun updatePhoneNumber(newNumber: String) {
        updateState {
            copy(
                phoneNumber = newNumber,
                isNextEnabled = newNumber.isValidPhoneNumber()
            )
        }
    }

    private fun String.isValidPhoneNumber(): Boolean {
        return length == PHONE_NUMBER_LENGTH &&
            PhoneNumberUtils.isGlobalPhoneNumber(this)
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