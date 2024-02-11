package com.shindra.acafsxb.core.network.bo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkAirplane(
    val manufacturer: String,
    val model: String,
    @SerialName("engine_type")
    val engineType: String,
    @SerialName("engine_thrust_lb_ft")
    val engineThrustLbFt: String? = null,
    @SerialName("max_speed_knots")
    val maxSpeedKnots: String? = null,
    @SerialName("cruise_speed_knots")
    val cruiseSpeedKnots: String? = null,
    @SerialName("ceiling_ft")
    val ceilingFt: String? = null,
    @SerialName("takeoff_ground_run_ft")
    val takeoffGroundRunFt: String? = null,
    @SerialName("landing_ground_roll_ft")
    val landingGroundRollFt: String? = null,
    @SerialName("gross_weight_lbs")
    val grossWeightLbs: String? = null,
    @SerialName("empty_weight_lbs")
    val emptyWeightLbs: String? = null,
    @SerialName("length_ft")
    val lengthFt: String? = null,
    @SerialName("height_ft")
    val heightFt: String? = null,
    @SerialName("wing_span_ft")
    val wingSpanFt: String? = null,
    @SerialName("range_nautical_miles")
    val rangeNauticalMiles: String? = null
)