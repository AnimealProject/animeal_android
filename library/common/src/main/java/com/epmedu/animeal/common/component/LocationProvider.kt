package com.epmedu.animeal.common.component

import com.epmedu.animeal.common.data.model.MapLocation
import kotlinx.coroutines.flow.Flow

interface LocationProvider {
    fun fetchUpdates(): Flow<MapLocation>
}