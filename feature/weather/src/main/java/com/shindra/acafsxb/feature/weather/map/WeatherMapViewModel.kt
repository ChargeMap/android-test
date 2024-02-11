package com.shindra.acafsxb.feature.weather.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shindra.acafsxb.core.data.repositories.WeatherMapRepository
import com.shindra.acafsxb.core.data.repositories.WeatherRepository
import com.shindra.acafsxb.core.designsystem.components.UiState
import com.shindra.acafsxb.core.designsystem.components.asUiState
import com.shindra.acafsxb.core.model.MapAltitude
import com.shindra.acafsxb.core.model.MapType
import com.shindra.acafsxb.feature.weather.bo.WeatherMapUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


internal typealias WeatherMapUiState = UiState<List<WeatherMapUi>>

private const val OVERFLOW_LIMIT = 3

@HiltViewModel
internal class WeatherMapViewModel @Inject constructor(
    private val repo: WeatherRepository,
    private val mapRepo: WeatherMapRepository
) : ViewModel() {

    val mapUiState: MutableStateFlow<WeatherMapUiState> = MutableStateFlow(UiState.Loading)

    fun getMapsByMaturity(zone: String, type: MapType, mapAltitude: MapAltitude) {
        viewModelScope.launch {
            repo.mapByTerm(
                zone = zone,
                type = type,
                mapAltitude = mapAltitude
            )
                .map {
                    it.truncateIfOverflow(OVERFLOW_LIMIT)
                        .map { linkMap ->
                            WeatherMapUi(
                                type = linkMap.type,
                                zone = linkMap.zone,
                                validUntil = linkMap.validUntil,
                                utcTime = linkMap.utcTime,
                                uri = mapRepo.mapFileUri(linkMap.mapLink)
                            )
                        }
                }
                .asUiState()
                .collect {
                    mapUiState.value = it
                }
        }
    }

    //Todo migrate to business module ?
    private fun <T> List<T>.truncateIfOverflow(overflowLimit: Int): List<T> {
        return if (this.size > overflowLimit) {
            this.subList(0, overflowLimit)
        } else this
    }

}