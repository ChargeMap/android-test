package com.example.androidtest.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.androidtest.model.CarDataModel

@Composable
fun CarListComposable(carData: List<CarDataModel>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        items(carData) { car ->
            CarItemComposable(data = car)
        }
    }
}