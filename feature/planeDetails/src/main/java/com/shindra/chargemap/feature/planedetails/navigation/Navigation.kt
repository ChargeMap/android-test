package com.shindra.chargemap.feature.planedetails.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.shindra.chargemap.feature.planedetails.PlaneDetailScreenRoute


internal const val manufacturerArgumentKey = "manufacturer"
internal const val modelArgumentKey = "model"
const val planesDetailScreenRouteId = "planes_detail_screen_route/{$manufacturerArgumentKey}/{$modelArgumentKey}"
private const val planesDetailScreenRouteIdNoArg = "planes_detail_screen_route"


fun NavController.navigateToPlaneDetails(
    manufacturer: String,
    model: String,
    navOptions: NavOptions? = null
) {
    this.navigate("$planesDetailScreenRouteIdNoArg/$manufacturer/$model", navOptions)
}

fun NavGraphBuilder.planeDetails(
    onTitleChange: (String) -> Unit
) {
    composable(
        route = planesDetailScreenRouteId,
        arguments = listOf(
            navArgument(manufacturerArgumentKey) { type = NavType.StringType },
            navArgument(modelArgumentKey) { type = NavType.StringType }
        )
    ) { backStackEntry ->
        PlaneDetailScreenRoute(
            onTitleChange = onTitleChange,
            manufacturer = backStackEntry.arguments?.getString(manufacturerArgumentKey).orEmpty(),
            model = backStackEntry.arguments?.getString(modelArgumentKey).orEmpty()
        )
    }
}