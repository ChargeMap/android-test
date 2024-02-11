package com.shindra.acafsxb.feature.planes.compose

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AirlineSeatReclineNormal
import androidx.compose.material.icons.filled.ChairAlt
import androidx.compose.material.icons.filled.OilBarrel
import androidx.compose.material.icons.filled.Scale
import androidx.compose.material.icons.outlined.LineWeight
import androidx.compose.material.icons.outlined.Scale
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shindra.acafsxb.core.designsystem.components.CoilNetworkImage
import com.shindra.acafsxb.core.designsystem.components.ShimmerSurface
import com.shindra.acafsxb.feature.planes.R
import com.shindra.acafsxb.core.designsystem.theme.AcafSxbTheme

private typealias Registration = String

@Composable
internal fun PlaneCard(
    registration: String,
    hourlyCost: String,
    imageUrl: String,
    manufacturerAndType: String,
    minOilQuantityBadge: String,
    nbOfSeats: Int,
    mtow: String,
    onClick: (Registration) -> Unit
) {

    Card() {
        CoilNetworkImage(
            url = imageUrl,
            modifier = Modifier
                .height(350.dp)
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            contentScale = ContentScale.Crop,
            shape = MaterialTheme.shapes.medium
        )

        PlaneBanner(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(bottom = 8.dp),
            registration = registration,
            hourlyCost = hourlyCost,
            manufacturerAndType = manufacturerAndType
        )

        PlaneButton(
            onClick = { onClick(registration) },
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(bottom = 8.dp)
        )
    }
}

@Composable
private fun PlaneCardLoading() {
    Card(
        modifier = Modifier
            .height(400.dp)
            .fillMaxWidth()
    ) {
        ShimmerSurface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
        )
    }
}

@Composable
private fun PlaneBanner(
    modifier: Modifier = Modifier,
    registration: String,
    hourlyCost: String,
    manufacturerAndType: String
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = registration,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = manufacturerAndType,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Text(text = hourlyCost,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun PlaneSuggestionChips(
    nbOfSeats: Int,
    minOilQuantity: String,
    maxMass: String,
    modifier: Modifier = Modifier
) {
    val sModifier = Modifier.padding(end = 8.dp)

    Row(modifier = modifier.fillMaxWidth()) {
        SuggestionChip(
            icon = {
                Icon(
                    imageVector = Icons.Default.ChairAlt,
                    contentDescription = null
                )
            },
            onClick = {  },
            label = { Text(text = nbOfSeats.toString()) },
            modifier = sModifier
        )

        SuggestionChip(
            icon = {
                Icon(
                    imageVector = Icons.Default.Scale,
                    contentDescription = null
                )
            },
            onClick = {  },
            label = { Text(text = maxMass) },
            modifier = sModifier
        )

        SuggestionChip(
            icon = {
                Icon(
                    imageVector = Icons.Default.OilBarrel,
                    contentDescription = null
                )
            },
            onClick = {  },
            label = { Text(minOilQuantity) },
            modifier = sModifier
        )
    }
}

@Composable
private fun PlaneButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End
    ) {
        Button(onClick = { onClick() }) {
            Text(text = stringResource(id = R.string.mass_and_balance_action_title))
        }
    }
}

@Composable
@Preview
private fun PlaneCardLightPreview() {
    AcafSxbTheme {
        Column {
            PlaneCard(
                registration = "F-GBQG",
                hourlyCost = "134 €/h",
                imageUrl = "",
                onClick = {},
                manufacturerAndType = "Cessena 152",
                nbOfSeats = 4,
                minOilQuantityBadge = "min 7 USqt",
                mtow = "758 kg"

            )
        }
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun PlaneCardDarkPreview() {
    AcafSxbTheme {
        Column {
            PlaneCard(
                registration = "F-GBQG",
                hourlyCost = "134 €/h",
                imageUrl = "",
                onClick = {},
                manufacturerAndType = "Cessena 152",
                nbOfSeats = 4,
                minOilQuantityBadge = "min 7 USqt",
                mtow = "758 kg"
            )
        }
    }
}