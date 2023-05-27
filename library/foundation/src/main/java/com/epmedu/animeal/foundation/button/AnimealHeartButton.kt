package com.epmedu.animeal.foundation.button

import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.preview.AnimealPreview

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AnimealHeartButton(
    selected: Boolean,
    onChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    elevation: Dp = 0.dp,
) {
    val iconColor = if (selected) {
        MaterialTheme.colors.error
    } else {
        MaterialTheme.colors.secondaryVariant
    }

    Surface(
        modifier = modifier.size(32.dp),
        shape = CircleShape,
        elevation = elevation,
        onClick = {
            onChange(!selected)
        },
    ) {
        Icon(
            modifier = Modifier.requiredSize(16.dp),
            imageVector = Icons.Filled.Favorite,
            tint = iconColor,
            contentDescription = null
        )
    }
}

@AnimealPreview
@Composable
private fun SelectedAnimealHeartButtonPreview() {
    AnimealHeartButton(
        selected = true,
        onChange = {}
    )
}

@AnimealPreview
@Composable
private fun UnselectedAnimealHeartButtonPreview() {
    AnimealHeartButton(
        selected = false,
        onChange = {}
    )
}