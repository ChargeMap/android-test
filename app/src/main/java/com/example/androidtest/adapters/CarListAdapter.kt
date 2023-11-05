package com.example.androidtest.adapters

import com.example.androidtest.model.CarDataModel
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson

class CarDataModelAdapter : JsonAdapter<List<CarDataModel>>() {
    @FromJson
    override fun fromJson(reader: JsonReader): List<CarDataModel> {
        val cars = mutableListOf<CarDataModel>()
        reader.beginArray()
        while (reader.hasNext()) {
            val car = CarDataModel()
            reader.beginObject()
            while (reader.hasNext()) {
                when (reader.nextName()) {
                    "image" -> car.image = reader.nextString()
                    "title" -> car.title = reader.nextString()
                    "start_production" -> car.startProduction = reader.nextString()
                    "class" -> car.category = reader.nextString()
                }
            }
            reader.endObject()
            cars.add(car)
        }
        reader.endArray()
        return cars
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: List<CarDataModel>?) { }
}