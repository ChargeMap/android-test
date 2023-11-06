package com.example.androidtest.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.androidtest.R
import com.example.androidtest.model.CarDetailModel

@Composable
fun CarDetailComposable(carDetail: CarDetailModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.largeSpacing))
    ) {

        // First row with model details & technical data sheet
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Model details",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing)))

                Text("Model Name: ${carDetail.modelName}", style = MaterialTheme.typography.bodySmall)
                Text("Model Trim: ${carDetail.modelTrim}", style = MaterialTheme.typography.bodySmall)
                Text("Model Year: ${carDetail.modelYear}", style = MaterialTheme.typography.bodySmall)
                Text("Model Body: ${carDetail.modelBody}", style = MaterialTheme.typography.bodySmall)
                Text("Model Engine Position: ${carDetail.modelEnginePosition}", style = MaterialTheme.typography.bodySmall)
                Text("Model Engine CC: ${carDetail.modelEngineCc}", style = MaterialTheme.typography.bodySmall)

                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.largeSpacing)))

                Text(
                    text = "Technical data",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing)))

                Text("Drive Type: ${carDetail.modelDrive}", style = MaterialTheme.typography.bodySmall)
                Text("Transmission Type: ${carDetail.modelTransmissionType}", style = MaterialTheme.typography.bodySmall)
                Text("Seats: ${carDetail.modelSeats}", style = MaterialTheme.typography.bodySmall)
                Text("Doors: ${carDetail.modelDoors}", style = MaterialTheme.typography.bodySmall)
                Text("Weight (kg): ${carDetail.modelWeightKg}", style = MaterialTheme.typography.bodySmall)
                Text("Length (mm): ${carDetail.modelLengthMm}", style = MaterialTheme.typography.bodySmall)
                Text("Width (mm): ${carDetail.modelWidthMm}", style = MaterialTheme.typography.bodySmall)
                Text("Height (mm): ${carDetail.modelHeightMm}", style = MaterialTheme.typography.bodySmall)
                Text("Wheelbase (mm): ${carDetail.modelWheelbaseMm}", style = MaterialTheme.typography.bodySmall)
                Text("LKM Highway: ${carDetail.modelLkmHwy}", style = MaterialTheme.typography.bodySmall)
                Text("LKM Mixed: ${carDetail.modelLkmMixed}", style = MaterialTheme.typography.bodySmall)
                Text("LKM City: ${carDetail.modelLkmCity}", style = MaterialTheme.typography.bodySmall)
                Text("Fuel Capacity (L): ${carDetail.modelFuelCapL}", style = MaterialTheme.typography.bodySmall)
                Text("Sold in US: ${carDetail.modelSoldInUs}", style = MaterialTheme.typography.bodySmall)
                Text("CO2 Emissions: ${carDetail.modelCo2}", style = MaterialTheme.typography.bodySmall)
                Text("Make Display: ${carDetail.modelMakeDisplay}", style = MaterialTheme.typography.bodySmall)
                Text("Make Display: ${carDetail.makeDisplay}", style = MaterialTheme.typography.bodySmall)
                Text("Make Country: ${carDetail.makeCountry}", style = MaterialTheme.typography.bodySmall)
            }

            // Second row with motor specs & speed
            Column {
                Text(
                    text = "Motor specs",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing)))

                Text("Engine Cylinders: ${carDetail.modelEngineCyl}", style = MaterialTheme.typography.bodySmall)
                Text("Engine Type: ${carDetail.modelEngineType}", style = MaterialTheme.typography.bodySmall)
                Text("Valves Per Cylinder: ${carDetail.modelEngineValvesPerCyl}", style = MaterialTheme.typography.bodySmall)
                Text("Engine Power (PS): ${carDetail.modelEnginePowerPs}", style = MaterialTheme.typography.bodySmall)
                Text("Engine Torque (Nm): ${carDetail.modelEngineTorqueNm}", style = MaterialTheme.typography.bodySmall)
                Text("Engine Compression: ${carDetail.modelEngineCompression}", style = MaterialTheme.typography.bodySmall)
                Text("Engine Fuel: ${carDetail.modelEngineFuel}", style = MaterialTheme.typography.bodySmall)

                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.largeSpacing)))

                Text(
                    text = "Speed",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing)))

                Text("Top Speed (Kph): ${carDetail.modelTopSpeedKph}", style = MaterialTheme.typography.bodySmall)
                Text("0 to 100 Kph: ${carDetail.model0To100Kph}", style = MaterialTheme.typography.bodySmall)

                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.largeSpacing)))
            }
        }
    }
}