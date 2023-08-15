package com.epmedu.animeal.signup.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.handler.loading.LoadingHandler
import com.epmedu.animeal.signup.domain.GetSignUpStartDestinationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SignUpViewModel @Inject constructor(
    private val getSignUpStartDestinationUseCase: GetSignUpStartDestinationUseCase,
    private val loadingHandler: LoadingHandler
) : ViewModel(),
    StateDelegate<SignUpState> by DefaultStateDelegate(SignUpState()) {

    init {
        getStartDestination()
        collectLoadingState()
    }

    private fun getStartDestination() {
        updateState {
            SignUpState(startDestination = getSignUpStartDestinationUseCase())
        }
    }

    private fun collectLoadingState() {
        viewModelScope.launch {
            loadingHandler.isLoading.collect {
                updateState { copy(isLoading = it) }
            }
        }
    }
}