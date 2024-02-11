package com.shindra.acafsxb.feature.balance.navigation.bo

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.shindra.acafsxb.core.model.BalanceType


internal data class BalanceUi(
    val inputs: List<BalanceInputUi>
)

internal data class BalanceInputUi(
    val title: String,
    val icon: ImageVector,
    val range: ClosedFloatingPointRange<Float>,
    val unit: String,
    var currentValueState: MutableState<Float> = mutableFloatStateOf(range.start),
    val type: BalanceType
)

internal enum class FuelType(val volumetricMass: Double) {
    AvGaz(0.72), JetA1(0.8)
}

internal data class BalanceGraphUi(
    val envelops: List<GraphLine>,
    val minY: Float,
    val maxY: Float,
    val maxX: Float,
    val minX: Float,
    val xSteps : Float
)

internal data class GraphLine(
    val title: String,
    val color : Color,
    val points: List<FloatEntry>,
    val hasPersistentMarker : Boolean = false
)