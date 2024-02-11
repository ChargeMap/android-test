package com.shindra.acafsxb.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.shindra.acafsxb.feature.planedetails.navigation.navigateToPlaneDetails
import com.shindra.acafsxb.feature.planedetails.navigation.planeDetails
import com.shindra.acafsxb.feature.planes.navigation.planesScreen
import com.shindra.acafsxb.feature.planes.navigation.planesScreenRouteId


@Composable
fun AcafsxbNavHost(
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
