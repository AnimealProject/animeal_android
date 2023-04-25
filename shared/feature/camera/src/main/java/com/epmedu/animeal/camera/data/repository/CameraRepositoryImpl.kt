package com.epmedu.animeal.camera.data.repository

import android.net.Uri
import com.epmedu.animeal.api.storage.StorageApi
import com.epmedu.animeal.camera.domain.repository.CameraRepository
import com.epmedu.animeal.common.data.wrapper.ApiResult
import com.epmedu.animeal.common.domain.wrapper.ActionResult

internal class CameraRepositoryImpl(private val storageApi: StorageApi) : CameraRepository {
    override suspend fun uploadPhoto(fileName: String, fileUri: Uri): ActionResult {
        return storageApi.uploadFile(fileName, fileUri).toActionResult()
    }
}

private fun ApiResult<Unit>.toActionResult(): ActionResult {
    return when (this) {
        is ApiResult.Success -> ActionResult.Success
        is ApiResult.Failure -> ActionResult.Failure(error)
    }
}