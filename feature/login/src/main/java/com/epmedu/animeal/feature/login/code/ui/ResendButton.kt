package com.epmedu.animeal.feature.login.code.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.base.theme.AnimealTheme
import com.epmedu.animeal.base.theme.DarkGunMetal
import com.epmedu.animeal.base.theme.Gainsboro
import com.epmedu.animeal.feature.login.R
import com.epmedu.animeal.feature.login.code.CodeConfirmationViewModel
import java.text.DecimalFormat

@Composable
internal fun ResendButton(
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
            contentColor = DarkGunMetal,
            disabledContentColor = Gainsboro
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
private fun ResendButtonPreview() {
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