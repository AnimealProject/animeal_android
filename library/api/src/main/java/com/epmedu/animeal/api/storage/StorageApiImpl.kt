package com.epmedu.animeal.api.storage

import android.net.Uri
import androidx.core.net.toFile
import com.amplifyframework.core.Amplify
import com.epmedu.animeal.common.data.wrapper.ApiResult
import com.epmedu.animeal.extensions.suspendCancellableCoroutine
import kotlin.coroutines.resume

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
}