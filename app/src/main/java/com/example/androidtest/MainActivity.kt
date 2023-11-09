package com.example.androidtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.preference.PreferenceManager
import com.example.androidtest.ui.composables.feed.MainScreen
import com.example.androidtest.ui.theme.AndroidTestTheme
import com.example.androidtest.viewmodel.feed.NewsFeedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: NewsFeedViewModel by viewModels()
    private lateinit var darkThemeState: MutableState<Boolean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AndroidTestTheme(darkTheme = darkThemeState.value) {
                MainScreen(viewModel = viewModel)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        checkPreferences()
    }

    private fun checkPreferences() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this@MainActivity)
        darkThemeState =
            mutableStateOf(preferences.getBoolean(Constants.PREFERENCE_DARK_MODE, false))
    }
}