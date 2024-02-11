package com.shindra.acafsxb.feature.planes.bo

import androidx.annotation.StringRes
import com.shindra.acafsxb.core.model.ModelAirplane

internal interface SectionType {
    data class Header(@StringRes val title: Int) : SectionType
    data class Item(val plane: ModelAirplane) : SectionType
}


internal data class ListModel(@StringRes val title: Int, val items : List<ModelAirplane>)
