package com.epmedu.animeal.analytics

import android.os.Bundle

interface AnalyticsManager {

    fun sendAnalyticsEvent(event: String, screenId: String)
    fun sendAnalyticsEvent(event: String, bundle: Bundle?)
}