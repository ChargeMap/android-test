package com.shindra.chargemap.core.model

data class ModelDetailAirplane(
    val manufacturer: String,
    val model: String,
    val engineType: String,
    val engineThrustLbFt: String?,
    val maxSpeedKt: String?,
    val cruiseSpeedKt: String?,
    val ceilingFt: String?,
    val takeoffGroundRunFt: String?,
    val landingGroundRollFt: String?,
    val grossWeightLbs: String?,
    val emptyWeightLbs: String?,
    val lengthFt: String?,
    val heightFt: String?,
    val wingspanFt: String?,
    val rangeNauticalMiles: String?
)