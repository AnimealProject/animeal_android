package com.epmedu.animeal.profile.presentation.viewmodel.handler

import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.profile.presentation.ProfileInputFormEvent
import com.epmedu.animeal.profile.presentation.viewmodel.ProfileInputFormState

interface ProfileInputFormHandler : StateDelegate<ProfileInputFormState> {
    fun handleInputFormEvent(event: ProfileInputFormEvent)
}