package com.epmedu.animeal.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import javax.inject.Inject

class AnalyticsManagerImpl @Inject constructor(
    private val firebaseAnalytics: FirebaseAnalytics
) : AnalyticsManager {

    override fun sendAnalyticsEvent(event: String, screenId: String) {
        firebaseAnalytics.logEvent(event) {
            param(AnalyticsParameterKey.SCREEN_ID.key, screenId)
        }
    }

    override fun sendAnalyticsEvent(event: String, bundle: Bundle?) {
        firebaseAnalytics.logEvent(event, bundle)
    }
}