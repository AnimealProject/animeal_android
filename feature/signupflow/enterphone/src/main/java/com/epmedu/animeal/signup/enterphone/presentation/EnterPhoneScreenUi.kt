@file:OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)

package com.epmedu.animeal.signup.enterphone.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
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
import androidx.emoji2.text.EmojiCompat
import com.epmedu.animeal.foundation.button.AnimealShortButton
import com.epmedu.animeal.foundation.input.Flag
import com.epmedu.animeal.foundation.input.PhoneNumberInput
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.topbar.BackButton
import com.epmedu.animeal.foundation.topbar.TopBar
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

    BottomSheet(
        bottomSheetState,
        onEvent,
        onBack,
        state,
        focusRequester,
    ) {
        LazyColumn(
            modifier = Modifier.background(MaterialTheme.colors.background)
        ) {
            Region.values().forEach { region: Region ->
                item {
                    ListItem(
                        modifier = Modifier.clickable {
                            scope.launch { bottomSheetState.hide() }
                            onEvent(
                                EnterPhoneScreenEvent.RegionChosen(region)
                            )
                        },
                        text = {
                            Text(
                                region.codesListText()
                            )
                        },
                    )
                }
            }
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
        modifier = Modifier
            .background(Color.Black),
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

private fun Region.codesListText(): String {
    return "${EmojiCompat.get().process(flagEmoji())} ${phoneNumberCode()} ${countryName()}"
}

@AnimealPreview
@Composable
private fun EnterPhoneScreenPreview() {
    AnimealTheme {
        EnterPhoneScreenUi(
            focusRequester = FocusRequester(),
            state = EnterPhoneState(),
            onEvent = {},
            onBack = {}
        )
    }
}