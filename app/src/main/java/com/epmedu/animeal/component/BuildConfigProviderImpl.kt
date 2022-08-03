package com.epmedu.animeal.component

import com.epmedu.animeal.BuildConfig
import com.epmedu.animeal.common.component.BuildConfigProvider

internal class BuildConfigProviderImpl : BuildConfigProvider {

    override val appVersion: String = BuildConfig.VERSION_NAME
}