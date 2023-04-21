package com.epmedu.animeal.api.storage

import android.net.Uri
import androidx.core.net.toFile
import com.amplifyframework.core.Amplify
import com.amplifyframework.storage.options.StorageGetUrlOptions
import com.epmedu.animeal.common.data.wrapper.ApiResult
import com.epmedu.animeal.extensions.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

internal class StorageApiImpl : StorageApi {

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

    override suspend fun parseAmplifyUrl(imageId: String): String =
        suspendCancellableCoroutine {
            val options = StorageGetUrlOptions.builder().build()
            Amplify.Storage.getUrl(
                imageId,
                options,
                { resume(it.url.toString()) },
                { resumeWithException(it) }
            )
        }
}