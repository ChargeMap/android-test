@file:OptIn(ExperimentalMaterial3Api::class)

package com.shindra.acafsxb.feature.balance.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shindra.acafsxb.core.designsystem.components.BottomComponent
import com.shindra.acafsxb.core.designsystem.components.UiStateContent
import com.shindra.acafsxb.core.model.BalanceType
import com.shindra.acafsxb.feature.balance.R
import com.shindra.acafsxb.feature.balance.navigation.bo.BalanceGraphUi
import com.shindra.acafsxb.feature.balance.navigation.bo.GraphLine
import com.shindra.acafsxb.feature.balance.navigation.components.BalanceGraph
import com.shindra.acafsxb.feature.balance.navigation.components.BalanceSlider
import com.shindra.acafsxb.feature.balance.navigation.components.MassCard


/**
 * TODO :
 *  Add persistent marker to the balance line, to quickly know the important values (No fuel, current, full fuel). The current marker must disappear when matching with the full fuel.
 */

@Composable
internal fun PlaneBalanceScreenRoute(
    registration: String?,
    onTitleChange: (String) -> Unit,
    onBottomComponent: (BottomComponent) -> Unit,
    viewModel: PlaneBalanceViewModel = hiltViewModel()

) {
    val title = stringResource(id = R.string.plane_balance_screen_title, registration.orEmpty())

    LaunchedEffect(key1 = title) {
        onTitleChange(title)
    }

    val graphUi by viewModel.graph

    PlaneBalanceScreen(
        balanceUi = viewModel.uiState.collectAsState().value,
        onUpdateMass = viewModel::update,
        currentMass = viewModel.currentMass.intValue,
        remainingMass = viewModel.remainingMass.intValue,
        fuelEmport = viewModel.fuelAvailable.intValue,
        graphUi = graphUi,
        balanceLine = viewModel.balanceLine.value,
        isOverWeight = viewModel.isOverWeight.value,
        fuelMaxCapacity = viewModel.fuelCapacity.intValue
    )
}

@Composable
private fun PlaneBalanceScreen(
    balanceUi: PlaneBalanceUiState,
    graphUi: BalanceGraphUi?,
    onUpdateMass: (Float, BalanceType) -> Unit,
    currentMass: Int,
    remainingMass: Int,
    isOverWeight : Boolean,
    fuelEmport: Int,
    fuelMaxCapacity: Int,
    balanceLine: GraphLine?
) {

    UiStateContent(balanceUi) { ui ->
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 8.dp)
        ) {

            Column {
                MassCard(
                    currentMass = currentMass,
                    massRemaining = remainingMass,
                    fuelQuantityAvailable = fuelEmport,
                    fuelMaxQuantity = fuelMaxCapacity,
                    isOverWeight = isOverWeight,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                )

                ui.inputs.forEach { uEntry ->
                    BalanceSlider(
                        icon = uEntry.icon,
                        sliderValue = uEntry.currentValueState.value,
                        onSliderValueChange = { v ->
                            uEntry.currentValueState.value = v
                            onUpdateMass(v, uEntry.type)
                        },
                        unit = uEntry.unit,
                        name = uEntry.title,
                        valueRange = uEntry.range
                    )
                }
            }

            Text(
                text = stringResource(id = R.string.balance_graph_title),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            GraphBottomSheet(
                graphUi = graphUi,
                balanceLine = balanceLine,
            )
        }
    }
}

@Composable
private fun GraphBottomSheet(
    graphUi: BalanceGraphUi?,
    balanceLine: GraphLine?,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        BalanceGraph(
            graphUi = graphUi,
            balanceLine = balanceLine,
            modifier = Modifier
                .fillMaxHeight()
                .padding(top = 8.dp, start = 8.dp, end = 8.dp)
        )
    }
}