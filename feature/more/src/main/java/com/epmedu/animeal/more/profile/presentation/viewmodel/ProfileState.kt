package com.epmedu.animeal.more.profile.presentation.viewmodel

import com.epmedu.animeal.common.data.model.Profile
import com.epmedu.animeal.foundation.common.UiText

internal data class ProfileState(
    val profile: Profile = Profile(),
    val formState: FormState = FormState.READ_ONLY,
    val nameError: UiText? = null,
    val surnameError: UiText? = null,
    val emailError: UiText? = null,
    val birthDateError: UiText? = null,
) {
    val phoneNumber: String
        get() = profile.phoneNumber.replace("\\D".toRegex(), "")

    fun hasErrors() =
        listOfNotNull(nameError, surnameError, emailError, birthDateError).isNotEmpty()

    fun isEditedOrHasErrors() = formState == FormState.EDITED || hasErrors()

    enum class FormState {
        READ_ONLY,
        EDITABLE,
        EDITED
    }
}