package com.epmedu.animeal.signup.entercode.presentation.viewmodel

import com.epmedu.animeal.signup.entercode.domain.GetPhoneNumberUseCase
import com.epmedu.animeal.signup.entercode.domain.confirmcode.FacebookConfirmCodeUseCase
import com.epmedu.animeal.signup.entercode.domain.sendcode.FacebookSendCodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class FacebookEnterCodeViewModel @Inject constructor(
    sendCodeUseCase: FacebookSendCodeUseCase,
    confirmCodeUseCase: FacebookConfirmCodeUseCase,
    getPhoneNumberUseCase: GetPhoneNumberUseCase,
) : EnterCodeViewModel(sendCodeUseCase, confirmCodeUseCase, getPhoneNumberUseCase)