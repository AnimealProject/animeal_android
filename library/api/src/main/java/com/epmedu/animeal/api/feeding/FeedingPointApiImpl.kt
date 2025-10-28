package com.epmedu.animeal.api.feeding

import android.content.Context
import com.amplifyframework.api.graphql.SubscriptionType
import com.amplifyframework.datastore.generated.model.FeedingPoint
import com.epmedu.animeal.api.AnimealApi
import kotlinx.coroutines.flow.Flow
import com.epmedu.animeal.api.R
import com.google.gson.Gson
import kotlinx.coroutines.flow.flow

internal class FeedingPointApiImpl(
    private val animealApi: AnimealApi,
    private val context: Context
) : FeedingPointApi {

    data class FeedingPointWrapper(val FeedingPoints: List<FeedingPoint>)

    override fun getAllFeedingPoints(isGuest: Boolean): Flow<List<FeedingPoint>> = if (isGuest) {
        flow {
            context.resources.openRawResource(R.raw.guestmock).bufferedReader().use { reader ->
                val wrapper = Gson().fromJson(reader.readText(), FeedingPointWrapper::class.java)
                emit(wrapper.FeedingPoints)
            }
        }
    }
    else {
        animealApi.getModelList(FeedingPoint::class.java)
    }

    override fun subscribeToFeedingPointsUpdates(): Flow<FeedingPoint> {
        return animealApi.launchSubscription(SubscriptionType.ON_UPDATE, FeedingPoint::class.java)
    }

    override fun subscribeToFeedingPointsCreation(): Flow<FeedingPoint> {
        return animealApi.launchSubscription(SubscriptionType.ON_CREATE, FeedingPoint::class.java)
    }

    override fun subscribeToFeedingPointsDeletion(): Flow<FeedingPoint> {
        return animealApi.launchSubscription(SubscriptionType.ON_DELETE, FeedingPoint::class.java)
    }
}