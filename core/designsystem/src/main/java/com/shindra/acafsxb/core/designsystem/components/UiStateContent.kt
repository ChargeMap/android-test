package com.shindra.acafsxb.core.designsystem.components

import android.util.Log
import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map


@Composable
fun <T> UiStateContent(
    state: UiState<T>,
    onError: @Composable (Throwable) -> Unit = {},
    onLoading: @Composable () -> Unit = { FullLoadingScreen() },
    onSuccess: @Composable (T) -> Unit,
) {
    when (state) {
        is UiState.Error -> onError(state.throwable)
        UiState.Loading -> onLoading()
        is UiState.Success -> onSuccess(state.value)
    }
}

sealed interface UiState<out T> {
    data class Success<T>(val value: T) : UiState<T>
    data class Error(val throwable: Throwable) : UiState<Nothing>
    object Loading : UiState<Nothing>
}

fun <T> Flow<T>.asUiState(): Flow<UiState<T>> {
    return this
        .map<T, UiState<T>> {
            UiState.Success(it)
        }
        .catch {
            Log.e("Flow", "Error in downstream", it)
            emit(UiState.Error(it))
        }
}