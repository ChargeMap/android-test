package com.shindra.acafsxb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.shindra.acafsxb.ui.AcafsxbApp
import com.shindra.acafsxb.core.designsystem.theme.AcafSxbTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AcafSxbTheme {
                AcafsxbApp()
            }
        }
    }
}