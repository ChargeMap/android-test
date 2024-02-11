package com.shindra.acafsxb.feature.planes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chargemap.core.domain.usecases.PlaneCategory
import com.chargemap.core.domain.usecases.PlaneCategoryType
import com.chargemap.core.domain.usecases.PlaneUseCase
import com.shindra.acafsxb.core.designsystem.components.UiState
import com.shindra.acafsxb.core.designsystem.components.asUiState
import com.shindra.acafsxb.feature.planes.bo.ListModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

internal typealias PlaneUi = UiState<List<ListModel>>

@HiltViewModel
internal class PlanesViewModel @Inject constructor(
    private val useCase: PlaneUseCase
) : ViewModel() {

    val planesByCategoryState: StateFlow<PlaneUi> = useCase().map {
        it.toUiModel()
    }.asUiState().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = UiState.Loading
    )
}

private fun List<PlaneCategory>.toUiModel(): List<ListModel> {
    return map {
        ListModel(
            title = when (it.category) {
                PlaneCategoryType.Jet -> R.string.plane_jet_category_title
                PlaneCategoryType.Piston -> R.string.plane_piston_category_title
                PlaneCategoryType.PropJet -> R.string.plane_propjet_category_title
            },
            items = it.planes
        )
    }
}