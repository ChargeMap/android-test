package com.example.androidtest.model

import com.squareup.moshi.Json

data class CarDetailModel(
    @Json(name = "model_id") val modelId: String = "",
    @Json(name = "model_make_id") val modelMakeId: String = "",
    @Json(name = "model_name") val modelName: String = "",
    @Json(name = "model_trim") val modelTrim: String = "",
    @Json(name = "model_year") val modelYear: String = "",
    @Json(name = "model_body") val modelBody: String = "",
    @Json(name = "model_engine_position") val modelEnginePosition: String = "",
    @Json(name = "model_engine_cc") val modelEngineCc: String = "",
    @Json(name = "model_engine_cyl") val modelEngineCyl: String = "",
    @Json(name = "model_engine_type") val modelEngineType: String = "",
    @Json(name = "model_engine_valves_per_cyl") val modelEngineValvesPerCyl: String = "",
    @Json(name = "model_engine_power_ps") val modelEnginePowerPs: String = "",
    @Json(name = "model_engine_torque_nm") val modelEngineTorqueNm: String = "",
    @Json(name = "model_engine_compression") val modelEngineCompression: String = "",
    @Json(name = "model_engine_fuel") val modelEngineFuel: String = "",
    @Json(name = "model_top_speed_kph") val modelTopSpeedKph: String = "",
    @Json(name = "model_0_to_100_kph") val model0To100Kph: String = "",
    @Json(name = "model_drive") val modelDrive: String = "",
    @Json(name = "model_transmission_type") val modelTransmissionType: String = "",
    @Json(name = "model_seats") val modelSeats: String = "",
    @Json(name = "model_doors") val modelDoors: String = "",
    @Json(name = "model_weight_kg") val modelWeightKg: String = "",
    @Json(name = "model_length_mm") val modelLengthMm: String = "",
    @Json(name = "model_width_mm") val modelWidthMm: String = "",
    @Json(name = "model_height_mm") val modelHeightMm: String = "",
    @Json(name = "model_wheelbase_mm") val modelWheelbaseMm: String = "",
    @Json(name = "model_lkm_hwy") val modelLkmHwy: String = "",
    @Json(name = "model_lkm_mixed") val modelLkmMixed: String = "",
    @Json(name = "model_lkm_city") val modelLkmCity: String = "",
    @Json(name = "model_fuel_cap_l") val modelFuelCapL: String = "",
    @Json(name = "model_sold_in_us") val modelSoldInUs: String = "",
    @Json(name = "model_co2") val modelCo2: String = "",
    @Json(name = "model_make_display") val modelMakeDisplay: String = "",
    @Json(name = "make_display") val makeDisplay: String = "",
    @Json(name = "make_country") val makeCountry: String = ""
)