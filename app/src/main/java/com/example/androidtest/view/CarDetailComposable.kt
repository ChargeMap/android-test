package com.example.androidtest.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.androidtest.model.CarDetailModel

@Composable
fun CarDetailComposable(carDetail: CarDetailModel) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text("Model ID: ${carDetail.modelId}")
        Text("Make ID: ${carDetail.modelMakeId}")
        Text("Model Name: ${carDetail.modelName}")
        Text("Model Trim: ${carDetail.modelTrim}")
        Text("Model Year: ${carDetail.modelYear}")
        Text("Model Body: ${carDetail.modelBody}")
        Text("Engine Position: ${carDetail.modelEnginePosition}")
        Text("Engine CC: ${carDetail.modelEngineCc}")
        Text("Engine Cylinders: ${carDetail.modelEngineCyl}")
        Text("Engine Type: ${carDetail.modelEngineType}")
        Text("Valves per Cylinder: ${carDetail.modelEngineValvesPerCyl}")
        Text("Engine Power (PS): ${carDetail.modelEnginePowerPs}")
        Text("Engine Torque (Nm): ${carDetail.modelEngineTorqueNm}")
        Text("Engine Compression: ${carDetail.modelEngineCompression}")
        Text("Engine Fuel Type: ${carDetail.modelEngineFuel}")
        Text("Top Speed (kph): ${carDetail.modelTopSpeedKph}")
        Text("0 to 100 kph: ${carDetail.model0To100Kph}")
        Text("Drive Type: ${carDetail.modelDrive}")
        Text("Transmission Type: ${carDetail.modelTransmissionType}")
        Text("Seats: ${carDetail.modelSeats}")
        Text("Doors: ${carDetail.modelDoors}")
        Text("Weight (kg): ${carDetail.modelWeightKg}")
        Text("Length (mm): ${carDetail.modelLengthMm}")
        Text("Width (mm): ${carDetail.modelWidthMm}")
        Text("Height (mm): ${carDetail.modelHeightMm}")
        Text("Wheelbase (mm): ${carDetail.modelWheelbaseMm}")
        Text("LKM Highway: ${carDetail.modelLkmHwy}")
        Text("LKM Mixed: ${carDetail.modelLkmMixed}")
        Text("LKM City: ${carDetail.modelLkmCity}")
        Text("Fuel Capacity (L): ${carDetail.modelFuelCapL}")
        Text("Sold in US: ${carDetail.modelSoldInUs}")
        Text("CO2 Emissions: ${carDetail.modelCo2}")
        Text("Make Display: ${carDetail.modelMakeDisplay}")
        Text("Make Display: ${carDetail.makeDisplay}")
        Text("Make Country: ${carDetail.makeCountry}")
    }
}