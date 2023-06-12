package com.epmedu.animeal.camera.domain.repository

import android.net.Uri
import com.epmedu.animeal.common.domain.wrapper.ActionResult

interface CameraRepository {

    suspend fun uploadPhoto(fileName: String, fileUri: Uri): ActionResult<Unit>

    suspend fun deletePhoto(fileName: String): ActionResult<Unit>
}