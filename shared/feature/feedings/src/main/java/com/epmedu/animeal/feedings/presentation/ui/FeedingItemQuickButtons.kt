package com.epmedu.animeal.feedings.presentation.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epmedu.animeal.foundation.button.AnimealButton
import com.epmedu.animeal.foundation.button.AnimealSecondaryButtonOutlined
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R

@Composable
internal fun FeedingItemQuickButtons(
    onApproveClick: () -> Unit,
    onRejectClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val buttonWidth = 77.dp
    val buttonShape = RoundedCornerShape(12.dp)
    val contentPadding = PaddingValues(8.dp)

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AnimealButton(
            onClick = onApproveClick,
            modifier = Modifier
                .width(buttonWidth)
                .fillMaxHeight(),
            shape = buttonShape,
            contentPadding = contentPadding,
        ) {
            FeedingItemQuickButtonContent(
                iconRes = R.drawable.ic_checkmark,
                textRes = R.string.approve,
                textColor = Color.White
            )
        }
        AnimealSecondaryButtonOutlined(
            onClick = onRejectClick,
            modifier = Modifier
                .width(buttonWidth)
                .fillMaxHeight(),
            shape = buttonShape,
            contentPadding = contentPadding,
        ) {
            FeedingItemQuickButtonContent(
                iconRes = R.drawable.ic_cross,
                textRes = R.string.reject
            )
        }
    }
}

@Composable
private fun FeedingItemQuickButtonContent(
    @DrawableRes iconRes: Int,
    @StringRes textRes: Int,
    textColor: Color = Color.Unspecified,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(iconRes),
            contentDescription = stringResource(textRes)
        )
        Text(
            text = stringResource(id = textRes),
            color = textColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = (-0.2).sp
        )
    }
}

@AnimealPreview
@Composable
private fun FeedingItemQuickButtonsPreview() {
    AnimealTheme {
        FeedingItemQuickButtons(
            onApproveClick = {},
            onRejectClick = {},
            modifier = Modifier
                .height(200.dp)
                .padding(12.dp)
        )
    }
}