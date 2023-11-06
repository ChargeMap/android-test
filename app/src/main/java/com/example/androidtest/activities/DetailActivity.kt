package com.example.androidtest.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.androidtest.extension.getStringExtra
import com.example.androidtest.ui.theme.AppTheme
import com.example.androidtest.view.CarDetailComposable
import com.example.androidtest.viewmodel.CarDetailViewModel

/**
 * Vehicle's detail, including all information relative about a make / model / (year).
 * Data provider is CarApi (free to use as november 2023)
 * Some vehicle may not be present in their API and this page will require further work to handle correct linking between list & detail.
 */
class DetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val make = intent.getStringExtra("make", "")
            val model = intent.getStringExtra("model", "")
            val year = intent.getStringExtra("year", "")

            // Retrieve cars detail data
            if (make.isNotEmpty() && model.isNotEmpty()) {
                val viewModel = CarDetailViewModel(make, model, year)
                val carData by viewModel.carData.collectAsState()

                AppTheme {
                    Surface {
                        carData?.let { CarDetailComposable(it) }
                    }
                }
            }
        }
    }
}
