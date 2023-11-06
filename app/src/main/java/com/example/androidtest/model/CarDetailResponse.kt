package com.example.androidtest.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CarDetailResponse(
    @Json(name = "Trims") val trims: List<CarDetailModel>
)