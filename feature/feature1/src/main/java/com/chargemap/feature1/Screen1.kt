package com.chargemap.feature1

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
internal fun Screen1Route(
    onTitleChange: (String) -> Unit,
    onNavigateOut: () -> Unit,
    //Vm
) {

    Screen(onNavigateOut)

}

@Composable
private fun Screen(onNavigateOut: () -> Unit) {
    Button(onClick = onNavigateOut) {
        Text(text = "Hello from Screen one")
    }

}