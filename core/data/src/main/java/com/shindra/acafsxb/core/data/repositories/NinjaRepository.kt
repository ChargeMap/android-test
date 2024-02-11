package com.shindra.acafsxb.core.data.repositories

import com.shindra.acafsxb.core.data.flowAndEmit
import com.shindra.acafsxb.core.model.ModelAirplane
import com.shindra.acafsxb.core.network.sources.NinjaDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NinjaRepository @Inject constructor(private val client: NinjaDataSource) {

    fun planesByEngineType(type: String): Flow<List<ModelAirplane>> = flowAndEmit {
        client.planeByEngineType(type).map {
            ModelAirplane(manufacturer = it.manufacturer, model = it.model)
        }
    }
}