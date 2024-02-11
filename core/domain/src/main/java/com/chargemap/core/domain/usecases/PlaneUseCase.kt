package com.chargemap.core.domain.usecases

import com.shindra.acafsxb.core.data.repositories.NinjaRepository
import com.shindra.acafsxb.core.model.ModelAirplane
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

private typealias Airplanes = List<ModelAirplane>

sealed interface PlaneCategoryType {
    data object Jet : PlaneCategoryType
    data object Piston : PlaneCategoryType
    data object PropJet : PlaneCategoryType
}

open class PlaneUseCase @Inject constructor(private val ninjaRepository: NinjaRepository) {

   open operator fun invoke(): Flow<List<PlaneCategory>> {
        val jetFlow = ninjaRepository.planesByEngineType("jet")
        val pistonFlow = ninjaRepository.planesByEngineType("piston")
        val propFlow = ninjaRepository.planesByEngineType("propjet")

        return combine(
            flow = jetFlow,
            flow2 = pistonFlow,
            flow3 = propFlow
        ) { jet, piston, prop ->

            listOf(
                jet.toPlaneCategory(PlaneCategoryType.Jet),
                piston.toPlaneCategory(PlaneCategoryType.Piston),
                prop.toPlaneCategory(PlaneCategoryType.PropJet)
            )
        }
    }
}


data class PlaneCategory(val category: PlaneCategoryType, val planes: List<ModelAirplane>)

private fun Airplanes.toPlaneCategory(category: PlaneCategoryType) = PlaneCategory(category, this)