package com.shindra.acafsxb.feature.weather.bo

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.shindra.acafsxb.feature.weather.navigation.weatherMetarTafRouteId
import com.shindra.acafsxb.feature.weather.navigation.weatherTemsiRouteId
import com.shindra.acafsxb.feature.weather.navigation.weatherWinTemRouteId

internal data class WeatherMenuEntryUi(
    @DrawableRes val icon: Int,
    @StringRes val title: Int,
    val entry: WeatherMenuType
)

internal enum class WeatherMenuType(val route: String) {
    MetarTaf(weatherMetarTafRouteId),
    Temsi(weatherTemsiRouteId),
    WinTem(weatherWinTemRouteId)
}