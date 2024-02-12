package com.shindra.chargemap.feature.planedetails

import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chargemap.core.domain.usecases.PlaneDetailsUseCase
import com.shindra.chargemap.core.designsystem.components.UiState
import com.shindra.chargemap.core.designsystem.components.asUiState
import com.shindra.chargemap.core.model.ModelDetailAirplane
import com.shindra.chargemap.feature.planedetails.navigation.manufacturerArgumentKey
import com.shindra.chargemap.feature.planedetails.navigation.modelArgumentKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

internal typealias PlaneDetailUiState = UiState<List<PlaneDetailUi>>

@HiltViewModel
internal class PlaneDetailsViewModel @Inject constructor(
    useCase: PlaneDetailsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val manufacturer = savedStateHandle.get<String>(manufacturerArgumentKey).orEmpty()
    private val model = savedStateHandle.get<String>(modelArgumentKey).orEmpty()

    val planeDetailsState: StateFlow<PlaneDetailUiState> = useCase(manufacturer, model).map {
        it.toUiModel()
    }.asUiState()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState.Loading
        )

}

internal data class PlaneDetailUi(
    @StringRes val key: Int,
    val value: String,
    @StringRes val unit: Int? = null
)


private fun ModelDetailAirplane.toUiModel(): List<PlaneDetailUi> = buildList {
    add(
        PlaneDetailUi(
            key = R.string.plane_detail_manufacturer_title,
            value = manufacturer
        )
    )
    add(
        PlaneDetailUi(
            key = R.string.plane_detail_model_title,
            value = model
        )
    )
    add(
        PlaneDetailUi(
            key = R.string.plane_detail_engine_title,
            value = engineType
        )
    )

    engineThrustLbFt?.let {
        add(
            PlaneDetailUi(
                key = R.string.plane_detail_engine_power_title,
                value = it,
                unit = R.string.power_unit
            )
        )
    }

    maxSpeedKt?.let {
        add(
            PlaneDetailUi(
                key = R.string.plane_detail_vmax_title,
                value = it,
                unit = R.string.speed_limit
            )
        )
    }
    ceilingFt?.let {
        add(
            PlaneDetailUi(
                key = R.string.plane_detail_plafond_max_title,
                value = it,
                unit = R.string.ft_unit
            )
        )
    }
}