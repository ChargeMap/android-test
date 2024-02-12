package com.shindra.chargemap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.shindra.chargemap.ui.ChargeMapApp
import com.shindra.chargemap.core.designsystem.theme.ChargeMapTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChargeMapTheme {
                ChargeMapApp()
            }
        }
    }
}