package com.epmedu.animeal.home.presentation.ui.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R

@Composable
internal fun RouteTopBar(
    modifier: Modifier = Modifier,
    timeLeft: String,
    distanceLeft: String,
    onCancelClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(10.dp))
            .background(MaterialTheme.colors.surface)
            .height(56.dp)
            .padding(start = 18.dp, end = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = timeLeft, style = MaterialTheme.typography.h6)
            Text(
                text = distanceLeft,
                style = MaterialTheme.typography.body1,
                color = Color(0xFF898A8D)
            )
            Spacer(modifier = Modifier.weight(1F))
            IconButton(onClick = onCancelClick) {
                Icon(
                    modifier = Modifier.size(14.dp),
                    painter = painterResource(R.drawable.ic_cancel),
                    contentDescription = null
                )
            }
        }
    }
}

@AnimealPreview
@Composable
private fun RouteBarPreview() {
    AnimealTheme {
        RouteTopBar(
            modifier = Modifier,
            timeLeft = "23 min ",
            distanceLeft = "• 1km 300m",
            onCancelClick = {}
        )
    }
}