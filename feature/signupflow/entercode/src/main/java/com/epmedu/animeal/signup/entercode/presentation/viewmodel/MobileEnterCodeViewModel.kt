package com.epmedu.animeal.signup.entercode.presentation.viewmodel

import com.epmedu.animeal.signup.entercode.domain.GetPhoneNumberUseCase
import com.epmedu.animeal.signup.entercode.domain.confirmcode.MobileConfirmCodeUseCase
import com.epmedu.animeal.signup.entercode.domain.sendcode.MobileSendCodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class MobileEnterCodeViewModel @Inject constructor(
    sendCodeUseCase: MobileSendCodeUseCase,
    confirmCodeUseCase: MobileConfirmCodeUseCase,
    getPhoneNumberUseCase: GetPhoneNumberUseCase,
) : EnterCodeViewModel(sendCodeUseCase, confirmCodeUseCase, getPhoneNumberUseCase)