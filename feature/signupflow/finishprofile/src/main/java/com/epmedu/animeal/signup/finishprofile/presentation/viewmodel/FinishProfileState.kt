package com.epmedu.animeal.signup.finishprofile.presentation.viewmodel

import com.epmedu.animeal.profile.presentation.viewmodel.ProfileInputFormState

data class FinishProfileState(
    val profileInputFormState: ProfileInputFormState = ProfileInputFormState(),
    val isDoneButtonEnabled: Boolean = false,
    val nextDestination: FinishProfileNextDestination? = null
)
