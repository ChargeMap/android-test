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
import androidx.compose.material3.ExperimentalMaterial3Api
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PlaneCard(
    manufacturer: String,
    model: String,
    imageUrl: String,
    onClick: (Registration) -> Unit
) {

    Card(onClick = { onClick("") }) {
      /*  CoilNetworkImage(
            url = imageUrl,
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            contentScale = ContentScale.Crop,
            shape = MaterialTheme.shapes.medium
        )*/

        PlaneBanner(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(bottom = 8.dp),
            manufacturer = manufacturer,
            model = model
        )

    }
}

@Composable
private fun PlaneBanner(
    modifier: Modifier = Modifier,
    manufacturer: String,
    model: String
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {

        Text(
            text = manufacturer,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = model,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
@Preview
private fun PlaneCardLightPreview() {
    AcafSxbTheme {
        Column {
            PlaneCard(
                manufacturer = "Airbus",
                model = "A320",
                imageUrl = "",
                onClick = {}
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
                manufacturer = "Airbus",
                model = "A350",
                imageUrl = "",
                onClick = {},
            )
        }
    }
}