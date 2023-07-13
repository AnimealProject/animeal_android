package com.epmedu.animeal.router.domain

import com.epmedu.animeal.common.route.SignUpRoute
import kotlinx.coroutines.flow.SharedFlow

interface RouterRepository {

    fun getRefreshTokenExpirationFlow(): SharedFlow<Boolean>

    fun confirmRefreshTokenExpirationWasHandled()

    fun getSignUpStartDestination(): SignUpRoute

    fun setOnboardingAsSignUpStartDestination()

    fun setFinishProfileAsSignUpStartDestination()
}