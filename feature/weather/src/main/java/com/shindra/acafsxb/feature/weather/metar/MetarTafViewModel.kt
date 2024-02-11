package com.shindra.acafsxb.feature.weather.metar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shindra.acafsxb.core.data.repositories.WeatherRepository
import com.shindra.acafsxb.core.designsystem.components.UiState
import com.shindra.acafsxb.core.designsystem.components.asUiState
import com.shindra.acafsxb.core.model.OaciCode
import com.shindra.acafsxb.core.model.Opmet
import com.shindra.acafsxb.core.model.PlaneInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


internal typealias OpmetUiState = UiState<List<Opmet>>

@HiltViewModel
class MetarTafViewModel @Inject constructor(
    private val repository: WeatherRepository,
) : ViewModel() {

    val opmetUiState: StateFlow<OpmetUiState> = repository.metarTaf(
        listOf(
            OaciCode("LFST"),
            OaciCode("LFSN"),
            OaciCode("LFGA")
        )
    ).asUiState()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState.Loading
        )
}