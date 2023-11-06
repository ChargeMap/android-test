package com.example.androidtest.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.androidtest.model.CarDataModel

@Composable
fun CarListComposable(data: List<CarDataModel?>?) {
    val configuration = LocalConfiguration.current
    val isTablet = configuration.screenWidthDp > 840 // arbitrary value to separate smartphone & tablet

    if (isTablet) {
        CarListComposableTablet(data)
    } else {
        CarListComposableSmartphone(data)
    }
}

@Composable
fun CarListComposableSmartphone(data: List<CarDataModel?>?) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        if (!data.isNullOrEmpty()) {
            items(data) { car ->
                car?.let { CarItemComposable(data = it) }
            }
        }
    }
}

@Composable
fun CarListComposableTablet(data: List<CarDataModel?>?) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp)
    ) {
        if (!data.isNullOrEmpty()) {
            items(data) { car ->
                car?.let { CarItemComposable(data = it) }
            }
        }
    }
}