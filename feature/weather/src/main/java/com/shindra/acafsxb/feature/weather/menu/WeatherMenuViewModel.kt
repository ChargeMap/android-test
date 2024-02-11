package com.shindra.acafsxb.feature.weather.menu

import androidx.lifecycle.ViewModel
import com.shindra.acafsxb.feature.weather.R
import com.shindra.acafsxb.feature.weather.bo.WeatherMenuEntryUi
import com.shindra.acafsxb.feature.weather.bo.WeatherMenuType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
internal class WeatherMenuViewModel @Inject constructor() : ViewModel() {

    val menuState: StateFlow<List<WeatherMenuEntryUi>> = MutableStateFlow(generateMenuEntry())

    private fun generateMenuEntry(): List<WeatherMenuEntryUi> {
        return listOf(
            WeatherMenuEntryUi(
                icon = R.drawable.ic_metar_taf,
                title = R.string.weather_metar_taf_menu_title,
                entry = WeatherMenuType.MetarTaf
            ),
            WeatherMenuEntryUi(
                icon = R.drawable.ic_temsi,
                title = R.string.weather_temsi_menu_title,
                entry = WeatherMenuType.Temsi
            ),
            WeatherMenuEntryUi(
                icon = R.drawable.ic_wind,
                title = R.string.weather_wintem_menu_title,
                entry = WeatherMenuType.WinTem
            )
        )
    }
}