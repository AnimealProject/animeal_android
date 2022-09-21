package com.epmedu.animeal.home.data

import com.epmedu.animeal.foundation.common.FeedStatus
import com.epmedu.animeal.home.data.model.Feeder
import com.epmedu.animeal.home.data.model.FeedingPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FeedingPointRepositoryImpl : FeedingPointRepository {

    override suspend fun getFeedingPoint(id: Int): Flow<FeedingPoint> = flowOf(
        FeedingPoint(
            id = -1,
            title = "Near to Bukia Garden M.S Technical University",
            status = FeedStatus.ORANGE,
            isFavourite = false,
            description = "This area covers about 100 sq.m. -S, it starts with Bukia Garden " +
                    "and Sports At the palace. There are about 1000 homeless people here " +
                    "The dog lives with the habit of helping You need.",
            lastFeeder = Feeder(
                id = -1,
                name = "Giorgi Abutidze",
                time = "14 hours ago"
            )
        )
    )
}