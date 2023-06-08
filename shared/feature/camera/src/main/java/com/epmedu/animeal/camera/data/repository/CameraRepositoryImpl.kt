package com.epmedu.animeal.camera.data.repository

import android.net.Uri
import com.epmedu.animeal.api.storage.StorageApi
import com.epmedu.animeal.camera.domain.repository.CameraRepository
import com.epmedu.animeal.common.data.wrapper.ApiResult
import com.epmedu.animeal.common.domain.wrapper.ActionResult

internal class CameraRepositoryImpl(private val storageApi: StorageApi) : CameraRepository {
    override suspend fun uploadPhoto(fileName: String, fileUri: Uri): ActionResult<Unit> {
        return storageApi.uploadFile(fileName, fileUri).toActionResult()
    }

    override suspend fun deletePhoto(fileName: String): ActionResult<Unit> =
        storageApi.deleteFile(fileName).toActionResult()
}

private fun ApiResult<Unit>.toActionResult(): ActionResult<Unit> {
    return when (this) {
        is ApiResult.Success -> ActionResult.Success(Unit)
        is ApiResult.Failure -> ActionResult.Failure(error)
    }
}