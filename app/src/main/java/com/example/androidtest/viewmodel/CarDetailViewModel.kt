package com.example.androidtest.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtest.api.CarApiService
import com.example.androidtest.model.CarDetailModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CarDetailViewModel(make: String, model: String, year: String) : ViewModel() {
    private val make: String
    private val model: String
    private val year: String
    val carData: MutableStateFlow<CarDetailModel> = MutableStateFlow(CarDetailModel())
    private val carApiService: CarApiService = CarApiService.getInstance()

    init {
        this.make = make
        this.model = model
        this.year = year
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                val response = carApiService.retrieveCar(make, model, year)
                carData.value = response
            } catch (e: Exception) {
                e.message?.let { Log.d(CarListViewModel::class.java.simpleName, it) }
            }
        }
    }
}