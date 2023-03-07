package com.epmedu.animeal.router.domain

import com.epmedu.animeal.common.route.SignUpRoute

interface RouterRepository {

    fun getSignUpStartDestination(): SignUpRoute

    fun setOnboardingAsSignUpStartDestination()

    fun setFinishProfileAsSignUpStartDestination()
}