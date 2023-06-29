package com.epmedu.animeal.feeding.presentation.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.epmedu.animeal.extensions.requestGpsByDialog
import com.epmedu.animeal.feeding.presentation.event.FeedingEvent
import com.epmedu.animeal.feeding.presentation.event.FeedingEvent.AgreeWillFeed
import com.epmedu.animeal.feeding.presentation.event.FeedingEvent.DismissWillFeed
import com.epmedu.animeal.feeding.presentation.viewmodel.WillFeedState
import com.epmedu.animeal.feeding.presentation.viewmodel.WillFeedState.CameraPermissionRequested
import com.epmedu.animeal.feeding.presentation.viewmodel.WillFeedState.ConfirmationRequested
import com.epmedu.animeal.feeding.presentation.viewmodel.WillFeedState.GeolocationPermissionRequested
import com.epmedu.animeal.feeding.presentation.viewmodel.WillFeedState.GpsSettingRequested
import com.epmedu.animeal.permissions.presentation.ui.CameraPermissionRequestDialog
import com.epmedu.animeal.permissions.presentation.ui.GeolocationPermissionRequestDialog

@Composable
fun WillFeedDialog(
    state: WillFeedState,
    onEvent: (FeedingEvent) -> Unit,
    onAgreeClick: () -> Unit
) {
    val context = LocalContext.current
    val locationEmbeddedDialogLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) {
            onEvent(DismissWillFeed)
        }

    when (state) {
        CameraPermissionRequested -> {
            CameraPermissionRequestDialog(onDismiss = { onEvent(DismissWillFeed) })
        }

        GeolocationPermissionRequested -> {
            GeolocationPermissionRequestDialog(
                onDismiss = { onEvent(DismissWillFeed) }
            )
        }

        GpsSettingRequested -> {
            LaunchedEffect(Unit) {
                context.requestGpsByDialog { intent ->
                    locationEmbeddedDialogLauncher.launch(
                        IntentSenderRequest.Builder(intent).build()
                    )
                }
            }
        }

        ConfirmationRequested -> {
            FeedingConfirmationDialog(
                onAgreeClick = {
                    onAgreeClick()
                    onEvent(AgreeWillFeed)
                },
                onCancelClick = { onEvent(DismissWillFeed) }
            )
        }

        else -> {}
    }
}