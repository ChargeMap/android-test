package com.shindra.acafsxb.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shindra.acafsxb.feature.planes.navigation.planesScreenRouteId

@Composable
fun rememberAcafsxbAppState(
    navController: NavHostController = rememberNavController()
) = remember(navController) {
    AcafsxbAppState(navController)
}

class AcafsxbAppState(val navController: NavHostController) {

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