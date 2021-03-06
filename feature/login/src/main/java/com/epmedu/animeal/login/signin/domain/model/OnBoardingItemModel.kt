package com.epmedu.animeal.login.signin.domain.model

import androidx.annotation.DrawableRes

internal data class OnBoardingItemModel(
    @DrawableRes val imageId: Int,
    val title: String,
    val text: String,
)
