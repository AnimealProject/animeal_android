package com.epmedu.animeal.router.data

import com.epmedu.animeal.common.route.SignUpRoute
import com.epmedu.animeal.router.domain.RouterRepository

class RouterRepositoryImpl : RouterRepository {

    private var signUpStartDestination: SignUpRoute = SignUpRoute.Onboarding

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