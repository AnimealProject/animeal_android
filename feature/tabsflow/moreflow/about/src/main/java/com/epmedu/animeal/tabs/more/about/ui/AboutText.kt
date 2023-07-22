package com.epmedu.animeal.tabs.more.about.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
internal fun AboutText(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier,
        letterSpacing = 0.sp,
        style = MaterialTheme.typography.body2,
        lineHeight = 20.sp,
    )
}