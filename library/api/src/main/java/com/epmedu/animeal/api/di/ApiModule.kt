package com.epmedu.animeal.api.di

import android.content.Context
import com.amazonaws.mobile.config.AWSConfiguration
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient
import com.amazonaws.mobileconnectors.appsync.sigv4.BasicCognitoUserPoolsAuthProvider
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool
import com.epmedu.animeal.api.faq.FAQApi
import com.epmedu.animeal.api.faq.FAQApiImpl
import com.epmedu.animeal.api.favourite.FavouriteApi
import com.epmedu.animeal.api.favourite.FavouriteApiImpl
import com.epmedu.animeal.api.feeding.FeedingPointApi
import com.epmedu.animeal.api.feeding.FeedingPointApiImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ApiModule {

    @Singleton
    @Provides
    fun providesAWSAppSyncClient(
        @ApplicationContext appContext: Context,
        awsConfiguration: AWSConfiguration
    ): AWSAppSyncClient {
        return AWSAppSyncClient.builder()
            .context(appContext)
            .awsConfiguration(awsConfiguration)
            .cognitoUserPoolsAuthProvider(
                BasicCognitoUserPoolsAuthProvider(
                    CognitoUserPool(appContext, awsConfiguration)
                )
            )
            .build()
    }

    @Singleton
    @Provides
    fun providesFeedingPointApi(
        awsAppSyncClient: AWSAppSyncClient
    ): FeedingPointApi = FeedingPointApiImpl(awsAppSyncClient)

    @Singleton
    @Provides
    fun providesFavouriteApi(
        awsAppSyncClient: AWSAppSyncClient
    ): FavouriteApi = FavouriteApiImpl(awsAppSyncClient)

    @Singleton
    @Provides
    fun providesFAQApi(): FAQApi = FAQApiImpl()
}