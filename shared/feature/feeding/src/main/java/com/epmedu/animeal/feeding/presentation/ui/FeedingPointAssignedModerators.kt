package com.epmedu.animeal.feeding.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.listitem.ExpandableListItem
import com.epmedu.animeal.foundation.theme.CustomColor
import com.epmedu.animeal.foundation.util.withLocalAlpha
import com.epmedu.animeal.resources.R

@Composable
internal fun FeedingPointAssignedModerators(
    moderators: List<String>,
    isExpandedInitially: Boolean = false,
    onExpanding: () -> Unit = {}
) {
    val configuration = LocalConfiguration.current
    var isExpanded by remember { mutableStateOf(isExpandedInitially) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        ExpandableListItem(
            text = {
                Text(
                    text = stringResource(id = R.string.assigned_moderators),
                    fontWeight = FontWeight.Bold,
                )
            },
            onClick = {
                if (isExpanded.not()) onExpanding()
                isExpanded = !isExpanded
            },
            modifier = Modifier.requiredWidth(configuration.screenWidthDp.dp),
            headerPadding = PaddingValues(horizontal = 8.dp),
            isExpanded = isExpanded,
            content = {
                Column(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    moderators.forEach { moderator ->
                        FeedingPointModerator(moderator = moderator)
                    }
                }
            }
        )
    }
}

@Composable
private fun FeedingPointModerator(moderator: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(CustomColor.LynxWhite.withLocalAlpha()),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_person),
                tint = CustomColor.Orange.withLocalAlpha(),
                contentDescription = null
            )
        }
        Text(
            text = moderator,
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.Medium,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colors.onSurface.withLocalAlpha(),
            maxLines = 1,
        )
    }
}
