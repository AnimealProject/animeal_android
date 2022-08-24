package com.epmedu.animeal.signup.finishprofile.presentation.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.button.AnimealButton
import com.epmedu.animeal.foundation.button.AnimealSecondaryButton
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R

@Composable
internal fun FinishProfileButtonsRow(
    modifier: Modifier = Modifier,
    onCancelClick: () -> Unit,
    onDoneClick: () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AnimealSecondaryButton(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.cancel),
            onClick = onCancelClick,
        )
        AnimealButton(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.done),
            onClick = onDoneClick,
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun FinishProfileButtonsRowPreview() {
    AnimealTheme {
        FinishProfileButtonsRow(
            onCancelClick = {},
            onDoneClick = {}
        )
    }
}