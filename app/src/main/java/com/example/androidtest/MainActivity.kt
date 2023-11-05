package com.example.androidtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.androidtest.view.CarListComposable
import com.example.androidtest.viewmodel.CarListViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Retrieve cars data
            val viewModel = CarListViewModel()
            val carData by viewModel.carData.collectAsState()

            Surface {
                CarListComposable(carData)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CarListViewPreview() {
    Surface {
        val viewModel = CarListViewModel()
        val carData by viewModel.carData.collectAsState()
        CarListComposable(carData)
    }
}