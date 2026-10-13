package com.epmedu.animeal.api.feeding

import android.content.Context
import com.amplifyframework.api.graphql.SubscriptionType
import com.amplifyframework.datastore.generated.model.FeedingPoint
import com.epmedu.animeal.api.AnimealApi
import com.epmedu.animeal.api.R
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class FeedingPointApiImpl(
    private val animealApi: AnimealApi,
    private val context: Context
) : FeedingPointApi {

    data class FeedingPointWrapper(
        @SerializedName("FeedingPoints")
        val feedingPoints: List<GetFeedingPointsQuery.GetFeedingPoint>
    )

    override fun getAllFeedingPoints(isGuest: Boolean): Flow<List<GetFeedingPointsQuery.GetFeedingPoint>> = if (isGuest) {
        flow {
            context.resources.openRawResource(R.raw.guestmock).bufferedReader().use { reader ->
                val wrapper = Gson().fromJson(reader.readText(), FeedingPointWrapper::class.java)
                emit(wrapper.feedingPoints)
            }
        }
    } else {
        flow {
            val items = animealApi.launchQuery(
                query = GetFeedingPointsQuery.builder().build(),
                responseClass = GetFeedingPointsQuery.Data::class.java
            )
            emit(items.data?.feedingPoints?.let { it } ?: emptyList())
        }
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