package com.epmedu.animeal.messages.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.epmedu.animeal.common.presentation.viewmodel.delegate.ActionDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.foundation.common.UiText
import com.epmedu.animeal.messages.presentation.event.MessageCreateDialogEvent
import com.epmedu.animeal.messages.presentation.event.MessageCreateDialogEvent.BodyChanged
import com.epmedu.animeal.messages.presentation.event.MessageCreateDialogEvent.BodyFocusCleared
import com.epmedu.animeal.messages.presentation.event.MessageCreateDialogEvent.Close
import com.epmedu.animeal.messages.presentation.event.MessageCreateDialogEvent.Confirm
import com.epmedu.animeal.messages.presentation.event.MessageCreateDialogEvent.Open
import com.epmedu.animeal.messages.presentation.viewmodel.MessageCreateDialogState.FormState.Confirmed
import com.epmedu.animeal.messages.presentation.viewmodel.MessageCreateDialogState.FormState.Opened
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MessageCreateDialogViewModel @Inject constructor(
    actionDelegate: ActionDelegate,
) : ViewModel(),
    StateDelegate<MessageCreateDialogState> by DefaultStateDelegate(initialState = MessageCreateDialogState()),
    ActionDelegate by actionDelegate {

    fun handleEvent(event: MessageCreateDialogEvent) {
        when (event) {
            is BodyChanged -> handleContentChangedEvent(event)
            is BodyFocusCleared -> validateContent()

            is Open -> openDialog()
            is Close -> closeDialog()

            is Confirm -> confirm()
        }
    }

    private fun confirm() {
        if (state.hasErrors()) {
            return
        }

        updateState { copy(formState = Confirmed) }
    }

    private fun validateContent() {
        var result: UiText = UiText.Empty
        if (state.body.isNotEmpty() && state.body.length > 300) {
            result = UiText.RawString("Should be less than 300 symbols")
        }
        updateState {
            copy(bodyError = result)
        }
    }

    private fun handleContentChangedEvent(event: BodyChanged) {
        updateState {
            copy(
                body = event.value,
                bodyError = UiText.Empty
            )
        }
    }

    private fun openDialog() {
        updateState {
            copy(formState = Opened)
        }
    }

    private fun closeDialog() {
        updateState { MessageCreateDialogState() }
    }
}