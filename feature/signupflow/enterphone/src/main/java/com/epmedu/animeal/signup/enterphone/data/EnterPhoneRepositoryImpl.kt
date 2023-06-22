package com.epmedu.animeal.signup.enterphone.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.common.data.wrapper.ApiResult
import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.extensions.edit
import com.epmedu.animeal.profile.data.util.updatePhoneNumber
import com.epmedu.animeal.profile.data.util.updatePhoneNumberRegion
import com.epmedu.animeal.profile.domain.model.Region
import com.epmedu.animeal.signup.enterphone.domain.EnterPhoneRepository
import javax.inject.Inject

internal class EnterPhoneRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val authAPI: AuthAPI,
) : EnterPhoneRepository {

    override suspend fun savePhoneNumberAndRegion(
        region: Region,
        phoneNumber: String
    ) {
        dataStore.edit {
            updatePhoneNumberRegion(region)
            updatePhoneNumber(phoneNumber)
        }
    }

    override suspend fun signUp(
        phone: String,
        password: String,
    ): ActionResult<Unit> {
        return when (val result = authAPI.signUp(phone, password)) {
            is ApiResult.Success -> ActionResult.Success(result.data)
            is ApiResult.Failure -> ActionResult.Failure(result.error)
        }
    }

    override suspend fun signIn(
        phoneNumber: String
    ): ActionResult<Unit> {
        return when (val result = authAPI.signIn(phoneNumber)) {
            is ApiResult.Success -> ActionResult.Success(result.data)
            is ApiResult.Failure -> ActionResult.Failure(result.error)
        }
    }
}