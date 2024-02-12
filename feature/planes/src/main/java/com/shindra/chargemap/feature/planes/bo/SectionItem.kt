package com.shindra.chargemap.feature.planes.bo

import androidx.annotation.StringRes

internal data class ListModel(@StringRes val title: Int, val items: List<UiAirplane>)

internal data class UiAirplane(
    val url: String,
    val model: String,
    val manufacturer: String
)