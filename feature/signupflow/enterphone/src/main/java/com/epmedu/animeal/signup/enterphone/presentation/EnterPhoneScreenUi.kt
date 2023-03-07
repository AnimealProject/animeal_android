@file:OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)

package com.epmedu.animeal.signup.enterphone.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.button.AnimealShortButton
import com.epmedu.animeal.foundation.input.Flag
import com.epmedu.animeal.foundation.input.PhoneNumberInput
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.topbar.BackButton
import com.epmedu.animeal.foundation.topbar.TopBar
import com.epmedu.animeal.profile.domain.model.Region
import com.epmedu.animeal.profile.domain.model.flagEmoji
import com.epmedu.animeal.profile.presentation.ui.RegionsLazyColumn
import com.epmedu.animeal.resources.R
import com.epmedu.animeal.signup.enterphone.presentation.EnterPhoneScreenEvent.NextButtonClicked
import com.epmedu.animeal.signup.enterphone.presentation.EnterPhoneScreenEvent.UpdatePhoneNumber
import com.epmedu.animeal.signup.enterphone.presentation.viewmodel.EnterPhoneState
import kotlinx.coroutines.launch

@Composable
internal fun EnterPhoneScreenUi(
    state: EnterPhoneState,
    focusRequester: FocusRequester,
    onEvent: (EnterPhoneScreenEvent) -> Unit,
    onBack: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    BackHandler(onBack = onBack)

    BottomSheet(
        bottomSheetState,
        onEvent,
        onBack,
        state,
        focusRequester,
    ) {
        RegionsLazyColumn(scope, bottomSheetState) { region ->
            onEvent(
                EnterPhoneScreenEvent.RegionChosen(region)
            )
        }
    }
}

@Composable
private fun BottomSheet(
    bottomSheetState: ModalBottomSheetState,
    onEvent: (EnterPhoneScreenEvent) -> Unit,
    onBack: () -> Unit,
    state: EnterPhoneState,
    focusRequester: FocusRequester,
    sheetContent: @Composable ColumnScope.() -> Unit
) {
    ModalBottomSheetLayout(
        scrimColor = Color.Transparent,
        sheetState = bottomSheetState,
        sheetContent = sheetContent
    ) {
        ScaffoldAndBody(
            onBack,
            state,
            onEvent,
            focusRequester,
            bottomSheetState
        )
    }
}

@Composable
private fun ScaffoldAndBody(
    onBack: () -> Unit,
    state: EnterPhoneState,
    onEvent: (EnterPhoneScreenEvent) -> Unit,
    focusRequester: FocusRequester,
    bottomSheetState: ModalBottomSheetState
) {
    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        topBar = {
            TopBar(
                title = stringResource(R.string.enter_phone_title),
                navigationIcon = {
                    BackButton(onClick = onBack)
                }
            )
        },
        floatingActionButton = {
            AnimealShortButton(
                text = stringResource(id = R.string.next),
                enabled = state.isNextEnabled,
                onClick = { onEvent(NextButtonClicked) }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 24.dp)
        ) {
            PhoneNumberInput(
                value = state.phoneNumber,
                prefix = state.prefix,
                modifier = Modifier
                    .padding(top = 56.dp)
                    .focusRequester(focusRequester),
                format = state.format,
                numberLength = state.numberLength,
                useNumberFormatter = state.region == Region.GE,
                // TODO use state.isDebug
                // TODO Update state.isDebug flag to be true for devs and QA, false for prod
                isFlagClickable = true,
                flag = if (state.region == Region.GE) {
                    Flag(R.drawable.ic_georgia)
                } else {
                    Flag(emojiFlag = state.region.flagEmoji())
                },
                onValueChange = { onEvent(UpdatePhoneNumber(it)) },
                onCountryClick = {
                    keyboardController?.hide()
                    scope.launch { bottomSheetState.show() }
                },
                error = if (state.isError) stringResource(id = R.string.enter_phone_error) else ""
            )
        }
    }
}

@AnimealPreview
@Composable
private fun EnterPhoneScreenPreview() {
    AnimealTheme {
        EnterPhoneScreenUi(
            focusRequester = FocusRequester(),
            state = EnterPhoneState(isDebug = true),
            onEvent = {},
            onBack = {}
        )
    }
}