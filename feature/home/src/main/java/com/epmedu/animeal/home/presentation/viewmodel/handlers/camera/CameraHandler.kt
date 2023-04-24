package com.epmedu.animeal.home.presentation.viewmodel.handlers.camera

import android.net.Uri
import com.epmedu.animeal.home.presentation.HomeScreenEvent
import kotlinx.coroutines.CoroutineScope

internal interface CameraHandler {
    fun CoroutineScope.handleCameraEvent(event: HomeScreenEvent.CameraEvent)

    suspend fun uploadFeedingPhoto(uri: Uri)

    fun deleteFeedingPhoto(uri: Uri)
}