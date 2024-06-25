package com.epmedu.animeal.home.presentation.model

sealed interface CameraState {
    data object Disabled : CameraState
    data object LoadingImageToServer : CameraState
    data object Enabled : CameraState
}