package com.epmedu.animeal.login.code.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.login.code.domain.EnterCodeViewModel
import com.epmedu.animeal.resources.R
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
            contentColor = LocalContentColor.current
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
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun ResendButtonPreview() {
    AnimealTheme {
        Surface {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                ResendButton(
                    isEnabled = true,
                    resendDelay = EnterCodeViewModel.RESEND_DELAY,
                    onClick = {}
                )
                Divider()
                ResendButton(
                    isEnabled = false,
                    resendDelay = EnterCodeViewModel.RESEND_DELAY,
                    onClick = {}
                )
            }
        }
    }
}