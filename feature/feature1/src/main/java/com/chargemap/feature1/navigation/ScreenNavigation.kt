package com.chargemap.feature1.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.chargemap.feature1.Screen1Route

const val screenOneRouteId = "screen1"

fun NavController.navigateToScreenOne(navOptions: NavOptions? = null) {
    this.navigate(screenOneRouteId, navOptions)
}

fun NavGraphBuilder.screenOne(
    onTitleChange: (String) -> Unit,
    onNavigateOut: () -> Unit
) {
    composable(
        route = screenOneRouteId
    ) {
        Screen1Route(
            onTitleChange = onTitleChange,
            onNavigateOut = onNavigateOut
        )
    }
}