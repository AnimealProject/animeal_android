package com.epmedu.animeal.feeding.presentation.ui

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.extensions.requestGpsByDialog
import com.epmedu.animeal.feeding.presentation.event.WillFeedEvent.ContinueWillFeed
import com.epmedu.animeal.feeding.presentation.event.WillFeedEvent.DismissWillFeed
import com.epmedu.animeal.feeding.presentation.viewmodel.WillFeedState.CameraPermissionRequested
import com.epmedu.animeal.feeding.presentation.viewmodel.WillFeedState.ConfirmationRequested
import com.epmedu.animeal.feeding.presentation.viewmodel.WillFeedState.DialogDismissed
import com.epmedu.animeal.feeding.presentation.viewmodel.WillFeedState.GeolocationPermissionRequested
import com.epmedu.animeal.feeding.presentation.viewmodel.WillFeedState.GpsSettingRequested
import com.epmedu.animeal.feeding.presentation.viewmodel.WillFeedViewModel
import com.epmedu.animeal.permissions.presentation.ui.CameraPermissionRequestDialog
import com.epmedu.animeal.permissions.presentation.ui.GeolocationPermissionRequestDialog

@Composable
fun WillFeedDialog(onAgreeClick: () -> Unit) {
    val viewModel: WillFeedViewModel = hiltViewModel()
    val state by viewModel.stateFlow.collectAsState()
    val context = LocalContext.current
    val locationEmbeddedDialogLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) {
            viewModel.handleEvent(ContinueWillFeed)
        }

    BackHandler(enabled = state != DialogDismissed) { /* Disable parent back handler */ }

    when (state) {
        CameraPermissionRequested -> {
            CameraPermissionRequestDialog(onDismiss = { viewModel.handleEvent(DismissWillFeed) })
        }

        GeolocationPermissionRequested -> {
            GeolocationPermissionRequestDialog(
                onDismiss = { viewModel.handleEvent(ContinueWillFeed) },
                onAgree = { viewModel.handleEvent(DismissWillFeed) }
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
                    viewModel.handleEvent(DismissWillFeed)
                },
                onCancelClick = { viewModel.handleEvent(DismissWillFeed) }
            )
        }

        DialogDismissed -> {}
    }
}