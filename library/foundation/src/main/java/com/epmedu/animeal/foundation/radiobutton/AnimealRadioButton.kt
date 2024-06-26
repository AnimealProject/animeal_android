package com.epmedu.animeal.foundation.radiobutton

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.spacer.WidthSpacer
import com.epmedu.animeal.foundation.theme.AnimealTheme

@Composable
fun AnimealRadioButton(
    selected: Boolean,
    onClick: () -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true
) {
    Row(
        modifier = modifier
            .selectable(
                selected = selected,
                onClick = onClick,
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                enabled = isEnabled,
                role = Role.RadioButton
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = null,
            modifier = Modifier.size(20.dp),
            enabled = isEnabled,
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colors.primary,
                unselectedColor = MaterialTheme.colors.primary
            ),
        )
        WidthSpacer(width = 10.dp)
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = label,
            style = MaterialTheme.typography.subtitle2
        )
    }
}

@AnimealPreview
@Composable
private fun AnimealRadioButtonPreview() {
    AnimealTheme {
        Column {
            AnimealRadioButton(
                selected = false,
                onClick = { },
                label = "Unselected"
            )
            Divider()
            AnimealRadioButton(
                selected = true,
                onClick = { },
                label = "Selected"
            )
            Divider()
            AnimealRadioButton(
                selected = true,
                onClick = { },
                isEnabled = false,
                label = "Disabled"
            )
        }
    }
}