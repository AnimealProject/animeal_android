package com.epmedu.animeal.home.presentation.viewmodel.handlers.gallery

import com.epmedu.animeal.camera.domain.usecase.DeletePhotoUseCase
import com.epmedu.animeal.common.presentation.viewmodel.delegate.ActionDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.feeding.presentation.ui.FeedingPhotoItem
import com.epmedu.animeal.home.presentation.HomeScreenEvent
import com.epmedu.animeal.home.presentation.model.CameraState
import com.epmedu.animeal.home.presentation.viewmodel.HomeState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal class DefaultFeedingPhotoGalleryHandler(
    private val deletePhoto: DeletePhotoUseCase,
    stateDelegate: StateDelegate<HomeState>,
    actionDelegate: ActionDelegate
) :
    FeedingPhotoGalleryHandler,
    StateDelegate<HomeState> by stateDelegate,
    ActionDelegate by actionDelegate {
    override fun CoroutineScope.handleGalleryEvent(event: HomeScreenEvent.FeedingGalleryEvent) {
        when (event) {
            is HomeScreenEvent.FeedingGalleryEvent.DeletePhoto -> showDialog(event.photo)
            is HomeScreenEvent.FeedingGalleryEvent.ConfirmDeletePhoto -> launch { deleteFeedingPhoto(event.photo) }
            HomeScreenEvent.FeedingGalleryEvent.CloseDeletePhotoDialog -> hideDialog()
        }
    }

    private fun hideDialog() {
        updateState {
            copy(
                deletePhotoItem = null
            )
        }
    }

    private fun showDialog(item: FeedingPhotoItem) {
        updateState {
            copy(deletePhotoItem = item)
        }
    }

    private suspend fun deleteFeedingPhoto(item: FeedingPhotoItem) {
        performAction(
            action = { deletePhoto(item.uri, item.name) },
            onSuccess = {
                updateState {
                    copy(
                        feedingPhotos = feedingPhotos - item,
                        cameraState = CameraState.Disabled,
                        deletePhotoItem = null
                    )
                }
            },
            onError = {
                // todo add error handling here
            }
        )
    }
}