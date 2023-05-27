package com.epmedu.animeal.signup.finishprofile.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.button.AnimealButton
import com.epmedu.animeal.foundation.button.AnimealSecondaryButtonOutlined
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R

@Composable
internal fun FinishProfileButtonsRow(
    onCancelClick: () -> Unit,
    onDoneClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AnimealSecondaryButtonOutlined(
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

@AnimealPreview
@Composable
private fun FinishProfileButtonsRowPreview() {
    AnimealTheme {
        FinishProfileButtonsRow(
            onCancelClick = {},
            onDoneClick = {}
        )
    }
}