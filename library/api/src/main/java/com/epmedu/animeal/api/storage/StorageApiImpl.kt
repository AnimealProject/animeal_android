package com.epmedu.animeal.api.storage

import android.net.Uri
import android.webkit.URLUtil
import androidx.core.net.toFile
import com.amplifyframework.core.Amplify
import com.amplifyframework.storage.options.StorageGetUrlOptions
import com.epmedu.animeal.common.data.wrapper.ApiResult
import com.epmedu.animeal.extensions.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

internal class StorageApiImpl : StorageApi {

    private val cachedImageUrls = mutableMapOf<String, String>()

    override suspend fun uploadFile(fileName: String, uri: Uri): ApiResult<Unit> {
        return suspendCancellableCoroutine {
            val file = uri.toFile()

            Amplify.Storage.uploadFile(
                fileName,
                file,
                { resume(ApiResult.Success(Unit)) },
                { resume(ApiResult.Failure(it)) }
            )
        }
    }

    override suspend fun parseAmplifyUrl(imageId: String): String {
        val cachedUrl = cachedImageUrls[imageId]
        return when {
            cachedUrl != null -> cachedUrl
            URLUtil.isValidUrl(imageId) -> imageId
            else -> suspendCancellableCoroutine {
                val options = StorageGetUrlOptions.builder().build()
                Amplify.Storage.getUrl(
                    imageId,
                    options,
                    { result ->
                        resume(
                            result.url.toString().also { cachedImageUrls[imageId] = it }
                        )
                    },
                    { resumeWithException(it) }
                )
            }
        }
    }

    override suspend fun deleteFile(fileName: String): ApiResult<Unit> =
        suspendCancellableCoroutine {
            Amplify.Storage.remove(
                fileName,
                { resume(ApiResult.Success(Unit)) },
                { resume(ApiResult.Failure(it)) }
            )
        }
}