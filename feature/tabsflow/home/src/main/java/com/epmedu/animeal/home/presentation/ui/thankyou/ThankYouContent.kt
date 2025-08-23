package com.epmedu.animeal.home.presentation.ui.thankyou

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.epmedu.animeal.foundation.common.AnimealPopUpScreen
import com.epmedu.animeal.foundation.icons.AnimealIcons
import com.epmedu.animeal.foundation.icons.colored.Paw
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R

@Composable
internal fun ThankYouContent(
    isAutoApproved: Boolean
) {
    BoxWithConstraints {
        AnimealPopUpScreen(
            imageVector = AnimealIcons.Colored.Paw,
            titleText = R.string.thank_you,
            subtitleText = when {
                isAutoApproved -> R.string.animals_fed
                else -> R.string.feeding_will_be_reviewed
            },
            subtitleModifier = Modifier.padding(horizontal = maxWidth / 5)
        )
    }
}

@AnimealPreview
@Composable
private fun ThankYouContentPreview() {
    AnimealTheme {
        Column {
            ThankYouContent(isAutoApproved = true)
            Divider()
            ThankYouContent(isAutoApproved = false)
        }
    }
}
