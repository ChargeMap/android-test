package com.example.androidtest.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtest.api.GithubService
import com.example.androidtest.model.CarDataModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CarListViewModel : ViewModel() {
    val carData: MutableStateFlow<List<CarDataModel?>?> = MutableStateFlow(null)
    private val githubService: GithubService = GithubService.getInstance()

    // Number of elements to display
    private val startIndex = 0
    private val endIndex = 50

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                val response = githubService.getCars().subList(startIndex, endIndex)
                carData.value = response
            } catch (e: Exception) {
                e.message?.let { Log.d(CarListViewModel::class.java.simpleName, it) }
            }
        }
    }
}