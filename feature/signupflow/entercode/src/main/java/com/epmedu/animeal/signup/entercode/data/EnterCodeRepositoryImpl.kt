package com.epmedu.animeal.signup.entercode.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.common.constants.DefaultConstants.PHONE_NUMBER_PREFIX
import com.epmedu.animeal.common.data.wrapper.ApiResult
import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.profile.data.util.phoneNumber
import com.epmedu.animeal.profile.data.util.phoneNumberRegion
import com.epmedu.animeal.profile.domain.model.Region
import com.epmedu.animeal.signup.entercode.domain.EnterCodeRepository
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
            val region = preferences.phoneNumberRegion
            val phoneNumber = preferences.phoneNumber
            val prefix = if (region == null) {
                PHONE_NUMBER_PREFIX
            } else {
                Region.valueOf(region.name).phoneNumberCode
            }
            prefix + phoneNumber
        }

    override suspend fun sendCode(): ActionResult<Unit> {
        return when (val result = authAPI.sendCode(phoneNumberWithPrefix.first())) {
            is ApiResult.Success -> ActionResult.Success(result.data)
            is ApiResult.Failure -> ActionResult.Failure(result.error)
        }
    }

    override suspend fun confirmSignIn(
        code: List<Int?>
    ): ActionResult<Unit> {
        return when (val result = authAPI.confirmSignIn(code.joinToString(""))) {
            is ApiResult.Success -> ActionResult.Success(result.data)
            is ApiResult.Failure -> ActionResult.Failure(result.error)
        }
    }

    override suspend fun confirmResendCode(
        code: List<Int?>
    ): ActionResult<Unit> {
        return when (val result = authAPI.confirmResendCode(code.joinToString(""))) {
            is ApiResult.Success -> ActionResult.Success(result.data)
            is ApiResult.Failure -> ActionResult.Failure(result.error)
        }
    }
}