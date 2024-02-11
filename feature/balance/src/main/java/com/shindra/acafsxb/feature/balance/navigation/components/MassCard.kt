package com.shindra.acafsxb.feature.balance.navigation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shindra.acafsxb.core.designsystem.components.KeyValue
import com.shindra.acafsxb.core.designsystem.components.KeyValueTextConfig
import com.shindra.acafsxb.core.designsystem.theme.AcafSxbTheme
import com.shindra.acafsxb.feature.balance.R

@Composable
fun MassCard(
    currentMass: Int,
    massRemaining: Int,
    fuelQuantityAvailable: Int,
    fuelMaxQuantity: Int,
    modifier: Modifier = Modifier,
    isOverWeight: Boolean = false
) {

    val kvModifier = Modifier.padding(8.dp)
    val valueTextConfig = if (isOverWeight) KeyValueTextConfig.defaultText(
        fontWeight = FontWeight.Bold
    ) else {
        KeyValueTextConfig.defaultText()
    }

    val cardColor = if (isOverWeight) CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.errorContainer
    ) else CardDefaults.cardColors()

    val fuelText = if (fuelQuantityAvailable > fuelMaxQuantity) {
        stringResource(id = R.string.full_fuel)
    } else {
        stringResource(id = R.string.fuel_quantity, fuelQuantityAvailable)
    }

    Card(
        modifier = modifier,
        colors = cardColor
    ) {
        KeyValue(
            key = stringResource(id = R.string.current_mass),
            value = stringResource(id = R.string.mass_quantity, currentMass),
            valueTextConfig = valueTextConfig,
            modifier = kvModifier
        )
        KeyValue(
            key = stringResource(id = R.string.remaining_mass),
            value = stringResource(id = R.string.mass_quantity, massRemaining),
            valueTextConfig = valueTextConfig,
            modifier = kvModifier
        )
        KeyValue(
            key = stringResource(id = R.string.fuel_quantity_allowed),
            value = fuelText,
            valueTextConfig = valueTextConfig,
            modifier = kvModifier
        )
    }
}

@Preview
@Composable
fun MassCardPreview() {
    AcafSxbTheme {
        Column {
            MassCard(
                currentMass = 758,
                massRemaining = 250,
                fuelQuantityAvailable = 100,
                fuelMaxQuantity = 100
            )

            MassCard(
                currentMass = 758,
                massRemaining = 250,
                fuelQuantityAvailable = -2,
                isOverWeight = true,
                fuelMaxQuantity = 100
            )
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun MassCardDarkPreview() {
    AcafSxbTheme {
        Column {
            MassCard(
                currentMass = 758,
                massRemaining = 250,
                fuelQuantityAvailable = 100,
                fuelMaxQuantity = 250
            )

            MassCard(
                currentMass = 758,
                massRemaining = 250,
                fuelQuantityAvailable = 100,
                isOverWeight = true,
                fuelMaxQuantity = 0
            )
        }

    }
}