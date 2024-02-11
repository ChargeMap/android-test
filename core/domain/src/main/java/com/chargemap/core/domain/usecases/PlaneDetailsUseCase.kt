package com.chargemap.core.domain.usecases

import com.shindra.acafsxb.core.data.repositories.NinjaRepository
import com.shindra.acafsxb.core.model.ModelDetailAirplane
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlaneDetailsUseCase @Inject constructor(private val ninjaRepository: NinjaRepository) {
    operator fun invoke(manufacturer: String, model: String): Flow<ModelDetailAirplane> =
        ninjaRepository.planeDetails(manufacturer, model)
}