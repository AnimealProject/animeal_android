package com.epmedu.animeal.analytics.di

import android.content.Context
import com.epmedu.animeal.analytics.AnalyticsManager
import com.epmedu.animeal.analytics.AnalyticsManagerImpl
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun providesFirebaseAnalytics(@ApplicationContext context: Context): FirebaseAnalytics =
        FirebaseAnalytics.getInstance(context)
}