package com.epmedu.animeal.api.push

import RegisterDeviceForPushNotificationsMutation
import com.epmedu.animeal.api.AnimealApi
import com.epmedu.animeal.common.data.wrapper.ApiResult

internal class PushApiImpl(
    private val animealApi: AnimealApi,
) : PushApi {

    override suspend fun registerDevice(deviceToken: String): ApiResult<String> {
        val mutation = RegisterDeviceForPushNotificationsMutation(
            deviceToken,
            PLATFORM
        )
        return animealApi.launchMutation(
            mutation = mutation,
            responseClass = String::class.java
        )
    }

    private companion object {
        const val PLATFORM = "ANDROID"
    }
}