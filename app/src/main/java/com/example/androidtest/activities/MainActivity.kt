package com.example.androidtest.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.androidtest.ui.theme.AppTheme
import com.example.androidtest.view.CarListComposable
import com.example.androidtest.viewmodel.CarListViewModel

/**
 * To keep things simple, MainActivity is the entry-point and responsible of the car's list.
 * Data provider is Github's raw json providing multiple make / model with their image.
 * It was selected as a correct provider because the JSON object is representative of any webservice
 * and the content fulfill the requirements of the test (image, title, subtitle).
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Retrieve cars data
            val viewModel = CarListViewModel()
            val carData by viewModel.carData.collectAsState()

            AppTheme {
                Surface {
                    CarListComposable(carData)
                }
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