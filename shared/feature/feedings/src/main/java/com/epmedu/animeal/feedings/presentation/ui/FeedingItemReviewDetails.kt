package com.epmedu.animeal.feedings.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.epmedu.animeal.feedings.presentation.model.FeedingModel
import com.epmedu.animeal.resources.R

@Composable
internal fun FeedingItemReviewDetails(
    feeding: FeedingModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        if (feeding.reviewedBy != null) {
            Text(
                text = stringResource(id = R.string.feeding_reviewed_by, feeding.reviewedBy),
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.onSurface
            )
        }
        if (feeding.rejectionReason != null) {
            Text(
                text = stringResource(id = R.string.feeding_rejection_reason, feeding.rejectionReason),
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.onSurface
            )
        }
    }
}