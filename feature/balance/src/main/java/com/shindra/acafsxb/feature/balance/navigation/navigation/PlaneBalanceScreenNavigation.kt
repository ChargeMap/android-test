package com.shindra.acafsxb.feature.balance.navigation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.shindra.acafsxb.core.designsystem.components.BottomComponent
import com.shindra.acafsxb.feature.balance.navigation.PlaneBalanceScreenRoute

internal const val registrationArgumentKey = "registration"
const val planesBalanceScreenRouteId = "planes_balance_screen_route/{$registrationArgumentKey}"
private const val planesBalanceScreenRouteIdNoArg = "planes_balance_screen_route"


fun NavController.navigateToPlanesBalanceFeature(
    registration : String,
    navOptions: NavOptions? = null
) {
    this.navigate("$planesBalanceScreenRouteIdNoArg/$registration", navOptions)
}

fun NavGraphBuilder.planesBalanceScreen(
    onTitleChange: (String) -> Unit,
    onBottomChange : (BottomComponent) -> Unit
) {
    composable(
        route = planesBalanceScreenRouteId,
        arguments = listOf(navArgument("registration") { type = NavType.StringType })
    ) { backStackEntry ->
        PlaneBalanceScreenRoute(
            onTitleChange = onTitleChange,
            onBottomComponent = onBottomChange,
            registration = backStackEntry.arguments?.getString(registrationArgumentKey)
        )
    }
}