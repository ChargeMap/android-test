package com.shindra.acafsxb.feature.balance.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Engineering
import androidx.compose.material.icons.filled.Groups2
import androidx.compose.material.icons.filled.LocalGasStation
import androidx.compose.material.icons.filled.Luggage
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patrykandpatrick.vico.core.entry.entryOf
import com.shindra.acafsxb.core.data.repositories.AcafsxbRepository
import com.shindra.acafsxb.core.designsystem.components.UiState
import com.shindra.acafsxb.core.model.BalanceType
import com.shindra.acafsxb.core.model.EnvelopCategory
import com.shindra.acafsxb.core.model.PlaneBalance
import com.shindra.acafsxb.feature.balance.navigation.bo.BalanceGraphUi
import com.shindra.acafsxb.feature.balance.navigation.bo.BalanceInputUi
import com.shindra.acafsxb.feature.balance.navigation.bo.BalanceUi
import com.shindra.acafsxb.feature.balance.navigation.bo.GraphLine
import com.shindra.acafsxb.feature.balance.navigation.bo.FuelType
import com.shindra.acafsxb.feature.balance.navigation.calculator.AircraftCalculator
import com.shindra.acafsxb.feature.balance.navigation.calculator.CalculatorEntry
import com.shindra.acafsxb.feature.balance.navigation.calculator.CalculatorResult
import com.shindra.acafsxb.feature.balance.navigation.navigation.registrationArgumentKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

internal typealias PlaneBalanceUiState = UiState<BalanceUi>

private const val X_STEPS = 4
private const val MAXY_ADJUST_THRESHOLD = 5

@HiltViewModel
internal class PlaneBalanceViewModel @Inject constructor(
    repository: AcafsxbRepository, savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val registration = savedStateHandle.get<String>(registrationArgumentKey).orEmpty()
    private lateinit var aircraftMassCalculator: AircraftCalculator

    private val _uiState: MutableStateFlow<PlaneBalanceUiState> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<PlaneBalanceUiState> = _uiState


    val currentMass: MutableIntState = mutableIntStateOf(0)
    val remainingMass: MutableIntState = mutableIntStateOf(0)
    val fuelAvailable: MutableIntState = mutableIntStateOf(0)
    val fuelCapacity : MutableIntState = mutableIntStateOf(0)
    val balanceLine: MutableState<GraphLine?> = mutableStateOf(null)

    val isOverWeight: MutableState<Boolean> = mutableStateOf(false)
    val graph: MutableState<BalanceGraphUi?> = mutableStateOf(null)

    private var mtow: Double = Double.POSITIVE_INFINITY

    init {
        viewModelScope.launch {
            repository.planeBalance(registration)
                .catch { _uiState.value = UiState.Error(it) }
                .collect { balanceModel ->
                    fuelCapacity.intValue = balanceModel.fuelCapacity
                    initMassCard(balanceModel)
                    initBalanceGraph(balanceModel)
                    _uiState.value = UiState.Success(
                        value = balanceModel.toUiModel()
                    )
                }
        }
    }

    private fun initMassCard(balanceModel: PlaneBalance) {
        aircraftMassCalculator = AircraftCalculator(
            fuelType = FuelType.AvGaz,
            emptyMass = balanceModel.emptyMass,
            emptyMoment = balanceModel.emptyMoment,
            entries = balanceModel.balanceEntry.map {
                CalculatorEntry(
                    value = it.minValue.toDouble(),
                    lever = it.leverValue,
                    type = it.type
                )
            },
            mtow = balanceModel.mtow.also { mtow = it }

        ).apply {
            calculateCurrent().also(::update)
        }
    }

    private fun initBalanceGraph(balanceModel: PlaneBalance) {
        val points = balanceModel.balanceEnvelops.flatMap { it.envelop }
        val minX = points.minOf { it.x.toFloat() }
        val maxX = points.maxOf { it.x.toFloat() }

        graph.value = BalanceGraphUi(
            envelops = balanceModel.balanceEnvelops.map { e ->
                GraphLine(
                    title = e.category.name,
                    points = e.envelop.map { entryOf(it.x, it.y) },
                    color = when (e.category) {
                        EnvelopCategory.N -> nCategoryColor
                        EnvelopCategory.U -> uCategoryColor
                        else -> Color(0xFF000000)
                    }
                )
            },
            maxX = maxX,
            maxY = points.maxOf { it.y.toFloat() },
            minX = minX,
            minY = points.minOf { it.y.toFloat() },
            xSteps = (maxX - minX) / X_STEPS
        )
    }

    private fun PlaneBalance.toUiModel(): BalanceUi {
        return BalanceUi(
            inputs = balanceEntry.map { balanceEntry ->
                BalanceInputUi(
                    title = balanceEntry.name,
                    unit = balanceEntry.unit,
                    range = balanceEntry.minValue..balanceEntry.maxValue,
                    type = balanceEntry.type,
                    icon = when (balanceEntry.type) {
                        BalanceType.CREW -> Icons.Default.Engineering
                        BalanceType.PASSENGER -> Icons.Default.Groups2
                        BalanceType.CARGO -> Icons.Default.Luggage
                        BalanceType.FUEL -> Icons.Default.LocalGasStation
                        else -> Icons.Default.ArrowBack
                    }
                )
            }
        )
    }

    fun update(value: Float, type: BalanceType) {
        aircraftMassCalculator.calculate(value, type).also(::update)
    }

    private fun update(result: CalculatorResult) {
        currentMass.intValue = result.currentMass
        remainingMass.intValue = result.remainingMass
        isOverWeight.value = result.remainingMass < 0
        fuelAvailable.intValue = result.remainingFuel

        balanceLine.value = balanceLine.value?.copy(
            points = result.balanceLine.map { entryOf(it.first, it.second) }
        ) ?: GraphLine(
            title = "Centrage",
            points = result.balanceLine.map { entryOf(it.first, it.second) },
            color = Color(0xFF00A1E4),
            hasPersistentMarker = true
        )

        val currentYmax = graph.value?.maxY

        if (currentYmax != null) {
            graph.value = graph.value?.copy(
                maxY = calculateNewYMax(
                    currentYMax = currentYmax,
                    currentMass = result.currentMass.toFloat(),
                    startingMaxY = mtow
                )
            )
        }
    }

    private fun calculateNewYMax(
        currentYMax: Float,
        currentMass: Float,
        startingMaxY: Double
    ): Float {
        return if (currentMass > currentYMax
            || (currentMass <= currentYMax && currentMass > startingMaxY)
        ) currentMass
        else startingMaxY.toFloat()
    }
}

private val nCategoryColor = Color(0xFF002157)
private val outOfBalanceColor = Color(0xFF931116)
private val uCategoryColor = Color(0xFF00A1E4)