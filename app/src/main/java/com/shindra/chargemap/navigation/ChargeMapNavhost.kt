package com.shindra.chargemap.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.shindra.chargemap.feature.planedetails.navigation.navigateToPlaneDetails
import com.shindra.chargemap.feature.planedetails.navigation.planeDetails
import com.shindra.chargemap.feature.planes.navigation.planesScreen
import com.shindra.chargemap.feature.planes.navigation.planesScreenRouteId


@Composable
fun ChargeMapNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = planesScreenRouteId,
    onTitleChange: (String) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        planesScreen(
            onTitleChange = onTitleChange,
            onMassAndBalanceClick = { manufacturer, model ->
                navController.navigateToPlaneDetails(
                    manufacturer = manufacturer,
                    model = model
                )
            }
        )

        planeDetails(onTitleChange)
    }
}
