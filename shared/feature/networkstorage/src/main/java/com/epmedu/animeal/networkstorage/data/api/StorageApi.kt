package com.epmedu.animeal.networkstorage.data.api

import android.net.Uri
import com.epmedu.animeal.common.data.wrapper.ApiResult

interface StorageApi {

    suspend fun uploadFile(fileName: String, uri: Uri): ApiResult<Unit>

    suspend fun getUrlFrom(fileName: String): String

    suspend fun deleteFile(fileName: String): ApiResult<Unit>
}