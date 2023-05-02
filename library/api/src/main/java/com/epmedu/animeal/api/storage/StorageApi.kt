package com.epmedu.animeal.api.storage

import android.net.Uri
import com.epmedu.animeal.common.data.wrapper.ApiResult

interface StorageApi {

    suspend fun uploadFile(fileName: String, uri: Uri): ApiResult<Unit>

    suspend fun parseAmplifyUrl(imageId: String): String

    suspend fun deleteFile(fileName: String): ApiResult<Unit>
}