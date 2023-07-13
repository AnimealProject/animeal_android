package com.epmedu.animeal.router.data

import com.epmedu.animeal.common.route.SignUpRoute
import com.epmedu.animeal.router.domain.RouterRepository
import com.epmedu.animeal.token.refreshtoken.RefreshTokenApi
import kotlinx.coroutines.flow.SharedFlow

class RouterRepositoryImpl(
    private val tokenApi: RefreshTokenApi
) : RouterRepository {

    private var signUpStartDestination: SignUpRoute = SignUpRoute.Onboarding

    override fun getRefreshTokenExpirationFlow(): SharedFlow<Boolean> {
        return tokenApi.getRefreshTokenExpirationFlow()
    }

    override fun confirmRefreshTokenExpirationWasHandled() {
        tokenApi.confirmRefreshTokenExpirationWasHandled()
    }

    override fun getSignUpStartDestination(): SignUpRoute {
        return signUpStartDestination
    }

    override fun setOnboardingAsSignUpStartDestination() {
        signUpStartDestination = SignUpRoute.Onboarding
    }

    override fun setFinishProfileAsSignUpStartDestination() {
        signUpStartDestination = SignUpRoute.FinishProfile
    }
}