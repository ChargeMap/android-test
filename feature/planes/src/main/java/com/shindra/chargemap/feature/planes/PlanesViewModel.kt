package com.shindra.chargemap.feature.planes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chargemap.core.domain.usecases.PlaneCategory
import com.chargemap.core.domain.usecases.PlaneCategoryType
import com.chargemap.core.domain.usecases.PlaneUseCase
import com.shindra.chargemap.core.designsystem.components.UiState
import com.shindra.chargemap.core.designsystem.components.asUiState
import com.shindra.chargemap.feature.planes.bo.ListModel
import com.shindra.chargemap.feature.planes.bo.UiAirplane
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

internal typealias PlaneUi = UiState<List<ListModel>>

@HiltViewModel
internal class PlanesViewModel @Inject constructor(
    private val useCase: PlaneUseCase
) : ViewModel() {

    private var _planesByCategoryState = MutableStateFlow<PlaneUi>(UiState.Loading)
    val planesByCategoryState: StateFlow<PlaneUi> = _planesByCategoryState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState.Loading
        )

    private var planeJob: Job? = null

    init {
        planes()
    }

    fun planes() {
        planeJob?.cancel()

        _planesByCategoryState.value = UiState.Loading
        viewModelScope.launch {
            network().collect {
                _planesByCategoryState.value = it
            }
        }
    }

    private fun network() = useCase()
        .map {
            it.toUiModel()
        }
        .asUiState()
}

private fun List<PlaneCategory>.toUiModel(): List<ListModel> {
    return map {
        val title: Int
        val url: String

        when (it.category) {
            PlaneCategoryType.Jet -> {
                title = R.string.plane_jet_category_title
                url =
                    "https://firebasestorage.googleapis.com/v0/b/acaf-sxb-3dc74.appspot.com/o/AirFranceA350-5.jpg?alt=media&token=6799c2fb-df68-446e-95bc-3898abb901e7"
            }

            PlaneCategoryType.Piston -> {
                title = R.string.plane_piston_category_title
                url =
                    "https://firebasestorage.googleapis.com/v0/b/acaf-sxb-3dc74.appspot.com/o/AirFranceA350-1.jpg?alt=media&token=42e442cf-0163-4ecb-b01e-38a15794031e"
            }

            PlaneCategoryType.PropJet -> {
                title = R.string.plane_propjet_category_title
                url =
                    "https://firebasestorage.googleapis.com/v0/b/acaf-sxb-3dc74.appspot.com/o/AirFranceA350-3.jpg?alt=media&token=bf458a38-ede6-43e6-94d5-54fda555a856"
            }
        }

        ListModel(
            title = title,
            items = it.planes.map {
                UiAirplane(
                    url = url,
                    model = it.model,
                    manufacturer = it.manufacturer
                )
            }
        )
    }
}