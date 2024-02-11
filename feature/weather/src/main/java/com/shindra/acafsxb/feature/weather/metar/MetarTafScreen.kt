@file:OptIn(ExperimentalMaterial3Api::class)

package com.shindra.acafsxb.feature.weather.metar

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shindra.acafsxb.core.model.OaciCode
import com.shindra.acafsxb.feature.weather.R

@Composable
internal fun MetarTafScreenRoute(
    onTitleChange: (String) -> Unit, viewModel: MetarTafViewModel = hiltViewModel()
) {

    val optmetState by viewModel.opmetUiState.collectAsStateWithLifecycle()

    MetarTafScreen(optmetState)
}

@Composable
internal fun MetarTafScreen(opmetState: OpmetUiState) {

    Log.d("T", "T")

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OpmetCard(
    airportName: String,
    airportOaciCode: OaciCode,
    hasTaf: Boolean,
    windDirection: Int,
    windSpeed: Int,
    qnh: Int,
    temperature: Int,
    dewPoint: Int,
    visibility: String,
    modifier: Modifier = Modifier,
    gustingSpeed: Int? = null,
    sealing: String? = null
) {
    Card(modifier = modifier.fillMaxWidth()) {


        ConstraintLayout(modifier.fillMaxWidth()) {
            val (name, oaciCode, windDirectionImage, windText, tafChips, pressureTemp, visi, celling) = createRefs()

            val pressureTempBarrier = createStartBarrier(pressureTemp)

            Text(text = airportName, modifier = Modifier.constrainAs(name) {
                top.linkTo(parent.top, margin = 8.dp)
                start.linkTo(parent.start, margin = 8.dp)
                end.linkTo(oaciCode.start, margin = 8.dp)
                width = Dimension.fillToConstraints
            })

            Text(text = airportOaciCode.code, modifier = Modifier.constrainAs(oaciCode) {
                top.linkTo(name.top)
                end.linkTo(parent.end, margin = 8.dp)
            })

            Image(painter = painterResource(id = R.drawable.ic_wind_direction),
                contentDescription = null,
                modifier = Modifier
                    .rotate(
                        windDirection.toFloat() - 180f
                    )
                    .constrainAs(windDirectionImage) {
                        top.linkTo(name.bottom, margin = 8.dp)
                        start.linkTo(name.start)
                    })

            Text(text = "${windDirection}° @ $windSpeed kt",
                modifier = Modifier.constrainAs(windText) {
                    top.linkTo(windDirectionImage.top)
                    start.linkTo(windDirectionImage.end, margin = 8.dp)
                    bottom.linkTo(windDirectionImage.bottom)
                    end.linkTo(pressureTempBarrier, margin = 8.dp)
                    width = Dimension.fillToConstraints
                })

            SuggestionChip(
                onClick = {},
                label = { Text("TAF") },
                enabled = hasTaf,
                modifier = Modifier.constrainAs(tafChips) {
                    end.linkTo(oaciCode.end)
                    top.linkTo(oaciCode.bottom)
                },
            )

            Column(modifier = Modifier.constrainAs(pressureTemp) {
                top.linkTo(tafChips.top, margin = 8.dp)
                end.linkTo(tafChips.start, margin = 8.dp)

            }) {
                Text(text = "$qnh hPa")
                Text(text = "$temperature °C / $dewPoint °C")
            }

            Text(text = visibility, modifier = Modifier.constrainAs(visi) {
                start.linkTo(name.start)

                width = Dimension.fillToConstraints
                bottom.linkTo(pressureTemp.bottom)
            })


        }


        /*  Row(
              modifier = Modifier.padding(bottom = 8.dp),
              verticalAlignment = Alignment.CenterVertically
          ) {

              Image(
                  painter = painterResource(id = R.drawable.ic_wind_direction),
                  contentDescription = null,
                  modifier = Modifier
                      .rotate(
                          windDirection.toFloat() - 180f
                      )
                      .padding(horizontal = 8.dp)

              )

              Text(text = "${windDirection}° @ ${windSpeed} kt")

          }*/


    }
}

@Preview
@Composable
fun OpmetCardPreview() {
    OpmetCard(
        airportName = "Strasbourg",
        airportOaciCode = OaciCode("LFST"),
        hasTaf = true,
        windDirection = 270,
        windSpeed = 11,
        qnh = 1013,
        visibility = "CAVOK",
        temperature = 15,
        dewPoint = 0
    )
}