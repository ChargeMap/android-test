package com.shindra.acafsxb.feature.planes.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.shindra.acafsxb.feature.planes.OnPlaneClick
import com.shindra.acafsxb.feature.planes.PlanesScreenRoute

const val planesScreenRouteId = "planes_screen_route"

fun NavController.navigateToPlanesFeature(navOptions: NavOptions? = null) {
    this.navigate(planesScreenRouteId, navOptions)
}

fun NavGraphBuilder.planesScreen(
    onTitleChange: (String) -> Unit,
    onMassAndBalanceClick: OnPlaneClick,
) {
    composable(
        route = planesScreenRouteId
    ) {
        PlanesScreenRoute(
            onTitleChange = onTitleChange,
            onPlaneClick = onMassAndBalanceClick
        )
    }
}