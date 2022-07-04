package com.epmedu.animeal.feature.login.code

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.epmedu.animeal.base.theme.AnimealTheme
import com.epmedu.animeal.base.theme.CursorColor
import com.epmedu.animeal.base.theme.DisabledButtonColor
import com.epmedu.animeal.base.theme.ResendButtonContentColor
import com.epmedu.animeal.base.ui.BackButton
import com.epmedu.animeal.base.ui.TopBar
import com.epmedu.animeal.feature.login.R
import com.epmedu.animeal.feature.login.code.CodeConfirmationViewModel.Companion.PHONE_NUMBER_PLACEHOLDER
import java.text.DecimalFormat

@Composable
internal fun CodeConfirmationScreenUi(
    state: CodeConfirmationScreenState,
    focusRequester: FocusRequester,
    onBack: () -> Unit,
    onDigitChange: (position: Int, digit: Int?) -> Unit,
    onResend: () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding(),
        topBar = {
            TopBar(
                title = "",
                navigationIcon = {
                    BackButton(onClick = onBack)
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(R.string.enter_verification_code),
                style = MaterialTheme.typography.h5
            )
            Text(
                text = "${stringResource(id = R.string.code_was_sent_to)} ${state.phoneNumber}",
                modifier = Modifier.padding(top = 8.dp)
            )
            CodeRow(
                code = state.code,
                isError = state.isCodeCorrect == false,
                focusRequester = focusRequester,
                modifier = Modifier.padding(top = 36.dp),
                onDigitChange = onDigitChange
            )
            Spacer(modifier = Modifier.weight(1f))
            ResendButton(
                isEnabled = state.isResendEnabled,
                resendDelay = state.resendDelay,
                onClick = onResend,
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
private fun CodeRow(
    code: List<Int?>,
    focusRequester: FocusRequester,
    onDigitChange: (position: Int, digit: Int?) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
) {
    val focusManager = LocalFocusManager.current

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        for (index in code.indices) {
            DigitField(
                digit = code[index],
                onDigitInput = { digit ->
                    onDigitChange(index, digit)
                    if (index == code.lastIndex) focusManager.clearFocus()
                    else focusManager.moveFocus(FocusDirection.Next)
                },
                onDigitRemove = {
                    onDigitChange(index, null)
                    if (index > 0) focusManager.moveFocus(FocusDirection.Previous)
                },
                modifier = if (index == 0) Modifier.focusRequester(focusRequester) else Modifier,
                isError = isError
            )
        }
    }
}

@Composable
private fun DigitField(
    digit: Int?,
    onDigitInput: (Int?) -> Unit,
    onDigitRemove: () -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false
) {
    OutlinedTextField(
        value = digit?.toString() ?: "",
        onValueChange = { value ->
            when {
                value.isOneDigit() -> onDigitInput(value.toInt())
                value.isEmpty() -> onDigitRemove()
            }
        },
        modifier = modifier.size(width = 66.dp, height = 72.dp),
        textStyle = TextStyle(fontSize = 26.sp, textAlign = TextAlign.Center),
        isError = isError,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = CursorColor,
            focusedBorderColor = Color.Gray
        )
    )
}

private fun String.isOneDigit() = isDigitsOnly() && length == 1

@Composable
private fun ResendButton(
    isEnabled: Boolean,
    resendDelay: Long,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val seconds = DecimalFormat("00").format(resendDelay)
    val text =
        if (isEnabled) stringResource(id = R.string.resend_code)
        else stringResource(id = R.string.resend_code_in, formatArgs = arrayOf(seconds))

    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = isEnabled,
        colors = ButtonDefaults.textButtonColors(
            contentColor = ResendButtonContentColor,
            disabledContentColor = DisabledButtonColor
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = text,
                modifier = Modifier.size(12.dp)
            )
            Text(
                text = text,
                style = MaterialTheme.typography.caption
            )
        }
    }
}

@Preview
@Composable
fun CodeRowPreview() {
    AnimealTheme {
        Surface {
            Column {
                CodeRow(
                    code = listOf(1, 2, null, null),
                    focusRequester = FocusRequester(),
                    onDigitChange = { _, _ -> },
                    modifier = Modifier.padding(8.dp)
                )
                Divider()
                CodeRow(
                    code = listOf(1, 2, 3, 9),
                    focusRequester = FocusRequester(),
                    onDigitChange = { _, _ -> },
                    modifier = Modifier.padding(8.dp),
                    isError = true
                )
            }
        }
    }
}

@Preview
@Composable
fun ResendButtonPreview() {
    AnimealTheme {
        Surface {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                ResendButton(
                    isEnabled = true,
                    resendDelay = CodeConfirmationViewModel.RESEND_DELAY,
                    onClick = {}
                )
                Divider()
                ResendButton(
                    isEnabled = false,
                    resendDelay = CodeConfirmationViewModel.RESEND_DELAY,
                    onClick = {}
                )
            }
        }
    }
}

@Preview
@Composable
fun CodeConfirmationScreenPreview() {
    AnimealTheme {
        CodeConfirmationScreenUi(
            state = CodeConfirmationScreenState(
                phoneNumber = PHONE_NUMBER_PLACEHOLDER,
                code = listOf(null, null, null, null)
            ),
            focusRequester = FocusRequester(),
            onBack = {},
            onDigitChange = { _, _ -> },
            onResend = {}
        )
    }
}