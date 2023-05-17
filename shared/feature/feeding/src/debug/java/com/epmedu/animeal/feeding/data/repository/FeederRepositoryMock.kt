package com.epmedu.animeal.feeding.data.repository

import android.text.format.DateUtils.DAY_IN_MILLIS
import android.text.format.DateUtils.HOUR_IN_MILLIS
import android.text.format.DateUtils.MINUTE_IN_MILLIS
import com.epmedu.animeal.feeding.domain.model.Feeder
import com.epmedu.animeal.feeding.domain.repository.FeederRepository
import java.util.Date
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FeederRepositoryMock : FeederRepository {

    override fun getFeeders(feedingPointId: String): Flow<List<Feeder>> {
        return flowOf(
            listOf(
                Feeder(
                    id = "-1",
                    name = "Giorgi",
                    surname = "Abutidze",
                    feedingDate = Date(
                        System.currentTimeMillis() -
                                (1..59).random()
                                    .times(
                                        listOf(DAY_IN_MILLIS, HOUR_IN_MILLIS, MINUTE_IN_MILLIS)
                                            .random()
                                    )
                    )
                )
            )
        )
    }
}