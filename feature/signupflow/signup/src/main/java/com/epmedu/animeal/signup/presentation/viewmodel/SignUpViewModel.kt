package com.epmedu.animeal.signup.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.signup.domain.GetSignUpStartDestinationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class SignUpViewModel @Inject constructor(
    private val getSignUpStartDestinationUseCase: GetSignUpStartDestinationUseCase
) : ViewModel(),
    StateDelegate<SignUpState> by DefaultStateDelegate(SignUpState()) {

    init {
        updateState {
            SignUpState(startDestination = getSignUpStartDestinationUseCase())
        }
    }
}