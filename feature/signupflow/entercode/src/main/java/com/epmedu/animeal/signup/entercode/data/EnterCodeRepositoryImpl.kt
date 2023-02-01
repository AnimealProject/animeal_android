package com.epmedu.animeal.signup.entercode.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.auth.AuthRequestHandler
import com.epmedu.animeal.common.constants.DataStorePreferencesKey.phoneNumberKey
import com.epmedu.animeal.common.constants.DataStorePreferencesKey.phoneNumberRegionKey
import com.epmedu.animeal.common.constants.DefaultConstants.EMPTY_STRING
import com.epmedu.animeal.common.constants.DefaultConstants.PHONE_NUMBER_PREFIX
import com.epmedu.animeal.profile.domain.model.Region
import com.epmedu.animeal.profile.domain.model.phoneNumberCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class EnterCodeRepositoryImpl @Inject constructor(
    dataStore: DataStore<Preferences>,
    private val authAPI: AuthAPI,
) : EnterCodeRepository {

    override val phoneNumberWithPrefix: Flow<String> = dataStore.data
        .map { preferences ->
            val region = preferences[phoneNumberRegionKey]
            val phoneNumber = preferences[phoneNumberKey] ?: EMPTY_STRING
            val prefix = if (region == null) {
                PHONE_NUMBER_PREFIX
            } else {
                Region.valueOf(region).phoneNumberCode()
            }
            prefix + phoneNumber
        }

    override suspend fun sendCode(requestHandler: AuthRequestHandler) {
        authAPI.sendCode(phoneNumberWithPrefix.first(), requestHandler)
    }

    override fun confirmSignIn(
        code: List<Int?>,
        requestHandler: AuthRequestHandler
    ) {
        authAPI.confirmSignIn(code.joinToString(""), requestHandler)
    }

    override fun confirmResendCode(
        code: List<Int?>,
        requestHandler: AuthRequestHandler
    ) {
        authAPI.confirmResendCode(code.joinToString(""), requestHandler)
    }
}