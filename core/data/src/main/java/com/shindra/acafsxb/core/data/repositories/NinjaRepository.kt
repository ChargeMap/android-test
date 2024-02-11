package com.shindra.acafsxb.core.data.repositories

import com.shindra.acafsxb.core.data.flowAndEmit
import com.shindra.acafsxb.core.model.ModelAirplane
import com.shindra.acafsxb.core.model.ModelDetailAirplane
import com.shindra.acafsxb.core.network.sources.NinjaDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

open class NinjaRepository @Inject constructor(private val client: NinjaDataSource) {

    fun planesByEngineType(type: String): Flow<List<ModelAirplane>> = flowAndEmit {
        client.planeByEngineType(type).map {
            ModelAirplane(manufacturer = it.manufacturer, model = it.model)
        }
    }

    fun planeDetails(manufacturer: String, model: String): Flow<ModelDetailAirplane> = flowAndEmit {
        val plane = client.planeByManufacturerAndModel(manufacturer = manufacturer, model = model)

        ModelDetailAirplane(
            plane?.manufacturer.orEmpty(),
            plane?.model.orEmpty(),
            plane?.engineType.orEmpty(),
            plane?.engineThrustLbFt,
            plane?.maxSpeedKnots,
            plane?.cruiseSpeedKnots,
            plane?.ceilingFt,
            plane?.takeoffGroundRunFt,
            plane?.landingGroundRollFt,
            plane?.grossWeightLbs,
            plane?.emptyWeightLbs,
            plane?.lengthFt,
            plane?.heightFt,
            plane?.wingSpanFt,
            plane?.rangeNauticalMiles
        )
    }
}