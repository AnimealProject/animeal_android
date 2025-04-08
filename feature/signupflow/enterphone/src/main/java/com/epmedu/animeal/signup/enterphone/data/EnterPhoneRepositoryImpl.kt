package com.epmedu.animeal.signup.enterphone.data

import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.common.data.wrapper.ApiResult
import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.signup.enterphone.domain.EnterPhoneRepository
import javax.inject.Inject

internal class EnterPhoneRepositoryImpl @Inject constructor(
    private val authAPI: AuthAPI,
) : EnterPhoneRepository {

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