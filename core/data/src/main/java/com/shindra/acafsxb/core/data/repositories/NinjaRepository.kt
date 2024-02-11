package com.shindra.acafsxb.core.data.repositories

import com.shindra.acafsxb.core.model.Airplane
import com.shindra.acafsxb.core.network.sources.NinjaDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NinjaRepository @Inject constructor(private val client: NinjaDataSource) {
    fun planesInfo(): Flow<List<Airplane>> = flow {
        emit(
            client.planeByEngineType("jet").map {
                Airplane(it.manufacturer)
            }
        )
    }
}