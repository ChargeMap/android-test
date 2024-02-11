package com.shindra.acafsxb.feature.weather.navigation

import androidx.navigation.*
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.shindra.acafsxb.core.model.MapType
import com.shindra.acafsxb.core.model.MapZone
import com.shindra.acafsxb.feature.weather.bo.WeatherMenuType
import com.shindra.acafsxb.feature.weather.map.WeatherWeatherMapScreenRoute
import com.shindra.acafsxb.feature.weather.menu.WeatherMenuScreenRoute
import com.shindra.acafsxb.feature.weather.metar.MetarTafScreenRoute

internal const val weatherNavGraphRoute = "weather_graph"
const val weatherMenuScreenRouteId = "weather_menu_screen_route"
internal const val weatherMetarTafRouteId = "weatherMetarTafRouteId"
internal const val weatherTemsiRouteId = "weatherTemsiRouteId"
internal const val weatherWinTemRouteId = "weatherWintempsRouteId"

fun NavController.navigateToWeatherFeature(navOptions: NavOptions? = null) {
    this.navigate(weatherNavGraphRoute, navOptions)
}

fun NavGraphBuilder.weatherMenuScreen(
    onTitleChange: (String) -> Unit,
    navController: NavController
) {
    navigation(startDestination = weatherMenuScreenRouteId, route = weatherNavGraphRoute) {
        composable(
            route = weatherMenuScreenRouteId
        ) {
            WeatherMenuScreenRoute(
                onTitleChange = onTitleChange,
                onEntryClick = { type -> navController.navigateToWeatherEntryScreen(type) }
            )
        }
        composable(
            route = weatherTemsiRouteId,
        ) {
            WeatherWeatherMapScreenRoute(
                onTitleChange = onTitleChange,
                mapType = MapType.Temsi,
                mapZone = MapZone.France,
                availableAltitude = listOf()
            )
        }

        composable(
            route = weatherWinTemRouteId,
        ) {
            WeatherWeatherMapScreenRoute(
                onTitleChange = onTitleChange,
                mapType = MapType.Wintemp,
                mapZone = MapZone.Eur,
                availableAltitude = listOf()
            )
        }

        composable(
            route = weatherMetarTafRouteId
        ) {
            MetarTafScreenRoute(
                onTitleChange = onTitleChange
            )
        }
    }
}


internal fun NavController.navigateToWeatherEntryScreen(
    weatherMenuType: WeatherMenuType,
    navOptions: NavOptions? = null
) {
    this.navigate(weatherMenuType.route, navOptions)
}