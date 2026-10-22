package com.epmedu.animeal.analytics.di

import com.epmedu.animeal.analytics.AnalyticsManager
import com.epmedu.animeal.analytics.AnalyticsManagerImpl
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object AnalyticsModule {

    @Singleton
    @Provides
    fun providesAnalyticsManager(analytics: FirebaseAnalytics): AnalyticsManager =
        AnalyticsManagerImpl(analytics)

    @Singleton
    @Provides
    fun providesFirebaseAnalytics(): FirebaseAnalytics = Firebase.analytics
}