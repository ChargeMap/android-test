package com.example.androidtest.adapters

import android.util.Log
import com.example.androidtest.model.CarDetailModel
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson

class CarDetailAdapter : JsonAdapter<CarDetailModel>() {
    @FromJson
    override fun fromJson(reader: JsonReader): CarDetailModel {
        var modelId = ""
        var modelMakeId = ""
        var modelName = ""
        var modelTrim = ""
        var modelYear = ""
        var modelBody = ""
        var modelEnginePosition = ""
        var modelEngineCc = ""
        var modelEngineCyl = ""
        var modelEngineType = ""
        var modelEngineValvesPerCyl = ""
        var modelEnginePowerPs = ""
        var modelEngineTorqueNm = ""
        var modelEngineCompression = ""
        var modelEngineFuel = ""
        var modelTopSpeedKph = ""
        var model0To100Kph = ""
        var modelDrive = ""
        var modelTransmissionType = ""
        var modelSeats = ""
        var modelDoors = ""
        var modelWeightKg = ""
        var modelLengthMm = ""
        var modelWidthMm = ""
        var modelHeightMm = ""
        var modelWheelbaseMm = ""
        var modelLkmHwy = ""
        var modelLkmMixed = ""
        var modelLkmCity = ""
        var modelFuelCapL = ""
        var modelSoldInUs = ""
        var modelCo2 = ""
        var modelMakeDisplay = ""
        var makeDisplay = ""
        var makeCountry = ""

        reader.beginObject()
        var propertyName = reader.nextName()
        if (propertyName == "Trims") {
            reader.beginArray()
            // Case empty array
            if (reader.peek() == JsonReader.Token.END_ARRAY) {
                reader.endArray()
            } else {
                reader.beginObject()
            }
        }

        while (reader.hasNext() && (reader.peek() != JsonReader.Token.NULL)) {
            try {
                propertyName = reader.nextName()
                if (reader.peek() == JsonReader.Token.NULL) {
                    reader.skipValue()
                }
                else {
                    when (propertyName) {
                        "model_id" -> modelId = reader.nextString()
                        "model_make_id" -> modelMakeId = reader.nextString()
                        "model_name" -> modelName = reader.nextString()
                        "model_trim" -> modelTrim = reader.nextString()
                        "model_year" -> modelYear = reader.nextString()
                        "model_body" -> modelBody = reader.nextString()
                        "model_engine_position" -> modelEnginePosition = reader.nextString()
                        "model_engine_cc" -> modelEngineCc = reader.nextString()
                        "model_engine_cyl" -> modelEngineCyl = reader.nextString()
                        "model_engine_type" -> modelEngineType = reader.nextString()
                        "model_engine_valves_per_cyl" -> modelEngineValvesPerCyl = reader.nextString()
                        "model_engine_power_ps" -> modelEnginePowerPs = reader.nextString()
                        "model_engine_torque_nm" -> modelEngineTorqueNm = reader.nextString()
                        "model_engine_compression" -> modelEngineCompression = reader.nextString()
                        "model_engine_fuel" -> modelEngineFuel = reader.nextString()
                        "model_top_speed_kph" -> modelTopSpeedKph = reader.nextString()
                        "model_0_to_100_kph" -> model0To100Kph = reader.nextString()
                        "model_drive" -> modelDrive = reader.nextString()
                        "model_transmission_type" -> modelTransmissionType = reader.nextString()
                        "model_seats" -> modelSeats = reader.nextString()
                        "model_doors" -> modelDoors = reader.nextString()
                        "model_weight_kg" -> modelWeightKg = reader.nextString()
                        "model_length_mm" -> modelLengthMm = reader.nextString()
                        "model_width_mm" -> modelWidthMm = reader.nextString()
                        "model_height_mm" -> modelHeightMm = reader.nextString()
                        "model_wheelbase_mm" -> modelWheelbaseMm = reader.nextString()
                        "model_lkm_hwy" -> modelLkmHwy = reader.nextString()
                        "model_lkm_mixed" -> modelLkmMixed = reader.nextString()
                        "model_lkm_city" -> modelLkmCity = reader.nextString()
                        "model_fuel_cap_l" -> modelFuelCapL = reader.nextString()
                        "model_sold_in_us" -> modelSoldInUs = reader.nextString()
                        "model_co2" -> modelCo2 = reader.nextString()
                        "model_make_display" -> modelMakeDisplay = reader.nextString()
                        "make_display" -> makeDisplay = reader.nextString()
                        "make_country" -> makeCountry = reader.nextString()
                        else -> reader.skipValue()
                    }
                }
            } catch (e: Exception) {
                e.message?.let { Log.e(CarDetailAdapter::class.java.simpleName, it) }
            }
        }

        // Consume json to the end, in order to retrieve the value
        while (reader.peek() != JsonReader.Token.END_DOCUMENT) {
            if (reader.peek() == JsonReader.Token.END_ARRAY)
                reader.endArray()
            else if (reader.peek() == JsonReader.Token.END_OBJECT)
                reader.endObject()
            else
                reader.skipValue()
        }

        return CarDetailModel(
            modelId,
            modelMakeId,
            modelName,
            modelTrim,
            modelYear,
            modelBody,
            modelEnginePosition,
            modelEngineCc,
            modelEngineCyl,
            modelEngineType,
            modelEngineValvesPerCyl,
            modelEnginePowerPs,
            modelEngineTorqueNm,
            modelEngineCompression,
            modelEngineFuel,
            modelTopSpeedKph,
            model0To100Kph,
            modelDrive,
            modelTransmissionType,
            modelSeats,
            modelDoors,
            modelWeightKg,
            modelLengthMm,
            modelWidthMm,
            modelHeightMm,
            modelWheelbaseMm,
            modelLkmHwy,
            modelLkmMixed,
            modelLkmCity,
            modelFuelCapL,
            modelSoldInUs,
            modelCo2,
            modelMakeDisplay,
            makeDisplay,
            makeCountry
        )
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: CarDetailModel?) { }
}
