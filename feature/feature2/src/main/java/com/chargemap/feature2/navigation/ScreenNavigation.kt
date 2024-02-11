package com.chargemap.feature2.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.chargemap.feature2.Screen2Route

private const val screenOneRouteId = "screen2"

fun NavController.navigateToScreenTwo(navOptions: NavOptions? = null) {
    this.navigate(screenOneRouteId, navOptions)
}

fun NavGraphBuilder.screenTwo(
    onTitleChange: (String) -> Unit
) {
    composable(
        route = screenOneRouteId
    ) {
        Screen2Route(
            onTitleChange = onTitleChange
        )
    }
}