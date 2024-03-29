package com.epmedu.animeal.camera.domain.usecase

import android.net.Uri
import com.epmedu.animeal.camera.domain.repository.CameraRepository
import com.epmedu.animeal.common.domain.wrapper.ActionResult
import java.io.File

class DeletePhotoUseCase(private val cameraRepository: CameraRepository) {

    suspend operator fun invoke(uri: Uri, fileName: String): ActionResult<Unit> =
        deleteRemoteFile(fileName) {
            deleteLocalFile(uri)
        }

    private suspend fun deleteRemoteFile(
        fileName: String,
        success: () -> ActionResult<Unit>
    ): ActionResult<Unit> =
        when (val result = cameraRepository.deletePhoto(fileName)) {
            is ActionResult.Failure -> result
            is ActionResult.Success<*> -> success()
        }

    private fun deleteLocalFile(uri: Uri): ActionResult<Unit> =
        uri.path
            ?.let(::File)
            ?.takeIf { it.exists() }
            ?.run {
                delete()
                ActionResult.Success(Unit)
            }
            ?: ActionResult.Failure(IllegalStateException("Can't delete the file by path ${uri.path}"))
}