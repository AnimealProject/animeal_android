package com.epmedu.animeal.more.profile.presentation.viewmodel

import com.epmedu.animeal.common.data.model.Profile
import com.epmedu.animeal.foundation.common.UiText

internal data class ProfileState(
    val profile: Profile = Profile(),
    val formState: FormState = FormState.READ_ONLY,
    val nameError: UiText = UiText.Empty,
    val surnameError: UiText = UiText.Empty,
    val emailError: UiText = UiText.Empty,
    val birthDateError: UiText = UiText.Empty,
) {
    val phoneNumber: String
        get() = profile.phoneNumber.replace("\\D".toRegex(), "")

    fun hasErrors() =
        listOf(nameError, surnameError, emailError, birthDateError).any { it !is UiText.Empty }

    fun isEditedOrHasErrors() = formState == FormState.EDITED || hasErrors()

    enum class FormState {
        READ_ONLY,
        EDITABLE,
        EDITED
    }
}