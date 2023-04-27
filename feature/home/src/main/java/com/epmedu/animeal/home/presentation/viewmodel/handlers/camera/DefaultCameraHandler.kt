package com.epmedu.animeal.home.presentation.viewmodel.handlers.camera

import android.net.Uri
import com.epmedu.animeal.camera.domain.usecase.UploadPhotoUseCase
import com.epmedu.animeal.common.presentation.viewmodel.delegate.ActionDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.feeding.presentation.ui.FeedingPhotoItem
import com.epmedu.animeal.home.presentation.HomeScreenEvent
import com.epmedu.animeal.home.presentation.model.CameraState
import com.epmedu.animeal.home.presentation.viewmodel.HomeState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

internal class DefaultCameraHandler @Inject constructor(
    stateDelegate: StateDelegate<HomeState>,
    actionDelegate: ActionDelegate,
    private val uploadPhotoUseCase: UploadPhotoUseCase
) : CameraHandler,
    StateDelegate<HomeState> by stateDelegate,
    ActionDelegate by actionDelegate {

    override fun CoroutineScope.handleCameraEvent(event: HomeScreenEvent.CameraEvent) {
        when (event) {
            HomeScreenEvent.CameraEvent.OpenCamera -> openCamera()
            HomeScreenEvent.CameraEvent.CloseCamera -> closeCamera()
            is HomeScreenEvent.CameraEvent.TakeNewPhoto -> launch { uploadFeedingPhoto(event.uri) }
        }
    }

    private fun openCamera() {
        if (state.cameraState != CameraState.LoadingImageToServer) {
            updateState { copy(cameraState = CameraState.Enabled) }
        }
    }

    private fun closeCamera() {
        updateState { copy(cameraState = CameraState.Disabled) }
    }

    override suspend fun uploadFeedingPhoto(uri: Uri) {
        updateState { copy(cameraState = CameraState.LoadingImageToServer) }
        val newPhoto = FeedingPhotoItem(uri, "${state.currentFeedingPoint?.id}_feedPhoto_${UUID.randomUUID()}.jpg")
        performAction(
            action = {
                uploadPhotoUseCase(newPhoto.name, newPhoto.uri)
            },
            onSuccess = {
                updateState {
                    copy(
                        feedingPhotos = feedingPhotos + newPhoto,
                        cameraState = CameraState.Disabled
                    )
                }
            },
            onError = {
                updateState { copy(cameraState = CameraState.Disabled) }
            }
        )
    }
}