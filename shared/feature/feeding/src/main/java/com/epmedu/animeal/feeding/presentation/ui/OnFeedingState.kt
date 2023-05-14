package com.epmedu.animeal.feeding.presentation.ui

import android.app.PendingIntent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.epmedu.animeal.extensions.launchGpsSettings
import com.epmedu.animeal.extensions.requestGpsByDialog
import com.epmedu.animeal.feeding.presentation.event.WillFeedEvent
import com.epmedu.animeal.feeding.presentation.viewmodel.WillFeedState

@Composable
fun OnFeedingState(state: WillFeedState, onEvent: (WillFeedEvent) -> Unit, showEmbeddedDialog: (PendingIntent) -> Unit) {
    val context = LocalContext.current
    when {
        state.showMotivateUseGpsDialog -> MotivateUseGpsDialog(
            onConfirm = { onEvent(WillFeedEvent.OpenGpsSettings) },
            onDismiss = { onEvent(WillFeedEvent.DeclineUseGps) }
        )
        state.showLocationSettingsEmbeddedDialog -> {
            context.requestGpsByDialog(showEmbeddedDialog)
        }
        state.openGpsSettings -> {
            context.launchGpsSettings()
        }
    }
}