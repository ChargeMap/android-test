package com.shindra.acafsxb.feature.weather.bo

import android.net.Uri
import com.shindra.acafsxb.core.model.MapAltitude
import com.shindra.acafsxb.core.model.MapType
import com.shindra.acafsxb.core.model.MapZone
import java.time.LocalDateTime

internal data class WeatherMapUi(
    val type: String,
    val zone: String,
    val validUntil: LocalDateTime?,
    val utcTime: String,
    val uri: Uri
)