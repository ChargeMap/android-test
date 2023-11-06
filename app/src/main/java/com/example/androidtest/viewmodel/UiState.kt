package com.example.androidtest.viewmodel

sealed interface UiState<out T> {

    data class Loading(val firstLoading : Boolean = false) : UiState<Nothing>

    data class Success<T>(val data: T) : UiState<T>

    data class Error(val error: String) : UiState<Nothing>
}