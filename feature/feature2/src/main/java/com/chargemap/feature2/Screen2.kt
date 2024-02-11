package com.chargemap.feature2

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
internal fun Screen2Route(
    onTitleChange: (String) -> Unit
    //Vm
) {
    Screen()
}

@Composable
private fun Screen() {
    Text(text = "Hello from Screen two")
}