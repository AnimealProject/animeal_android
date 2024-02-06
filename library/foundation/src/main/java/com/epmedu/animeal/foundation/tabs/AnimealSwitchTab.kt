package com.epmedu.animeal.foundation.tabs

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex

/**
 * Shows a tab.
 *
 * @param titleResId current Tab string resource id.
 * @param selected current tab selected state.
 * @param onClick Called when this tab is clicked.
 * @param modifier The [Modifier].
 */
@Composable
fun AnimealSwitchTab(
    @StringRes titleResId: Int,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(all = 2.dp)
            .clip(shape = RoundedCornerShape(size = 9.dp))
            .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = titleResId),
            fontSize = 14.sp,
            modifier = Modifier.zIndex(1f),
            color = if (selected) Color.White else MaterialTheme.colors.onSurface
        )
    }
}