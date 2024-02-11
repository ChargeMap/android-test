package com.shindra.acafsxb.feature.planes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shindra.acafsxb.core.data.repositories.NinjaRepository
import com.shindra.acafsxb.core.designsystem.components.UiState
import com.shindra.acafsxb.core.designsystem.components.asUiState
import com.shindra.acafsxb.core.model.Airplane
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

internal typealias PlaneInfoState = UiState<List<Airplane>>

@HiltViewModel
internal class PlanesViewModel @Inject constructor(
    private val repository: NinjaRepository
) : ViewModel() {

    val planeUiState: StateFlow<PlaneInfoState> = repository
        .planesInfo()
        .asUiState()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState.Loading
        )
}