package com.shindra.chargemap.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shindra.chargemap.feature.planes.navigation.planesScreenRouteId

@Composable
fun rememberChargeMapAppState(
    navController: NavHostController = rememberNavController()
) = remember(navController) {
    ChargeMapAppState(navController)
}

class ChargeMapAppState(val navController: NavHostController) {

    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val shouldShowNavUp: Boolean
        @Composable get() {
            val cDestination = currentDestination?.route ?: return false
            return cDestination != planesScreenRouteId
        }

    fun navigateUp() {
        navController.navigateUp()
    }

}