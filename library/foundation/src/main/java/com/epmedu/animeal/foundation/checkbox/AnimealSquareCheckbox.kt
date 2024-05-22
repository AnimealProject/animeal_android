package com.epmedu.animeal.foundation.checkbox

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.spacer.WidthSpacer
import com.epmedu.animeal.foundation.text.AutoSizeText
import com.epmedu.animeal.foundation.theme.AnimealTheme

@Composable
fun AnimealSquareCheckbox(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.size(20.dp),
            enabled = isEnabled,
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colors.primary,
                uncheckedColor = MaterialTheme.colors.primary,
            ),
        )
        WidthSpacer(width = 6.dp)
        AutoSizeText(text = label)
    }
}

@AnimealPreview
@Composable
private fun AnimealCheckboxPreview() {
    AnimealTheme {
        Column {
            AnimealSquareCheckbox(
                isChecked = false,
                onCheckedChange = { },
                label = "Unchecked"
            )
            Divider()
            AnimealSquareCheckbox(
                isChecked = true,
                onCheckedChange = { },
                label = "Checked"
            )
            Divider()
            AnimealSquareCheckbox(
                isChecked = true,
                onCheckedChange = { },
                isEnabled = false,
                label = "Disabled"
            )
        }
    }
}