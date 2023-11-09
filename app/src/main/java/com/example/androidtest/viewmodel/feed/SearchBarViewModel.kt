package com.example.androidtest.viewmodel.feed

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtest.util.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchBarViewModel @Inject constructor(val dispatcherProvider: DispatcherProvider) :
    ViewModel() {

    private val _searchWidgetState = mutableStateOf(SearchWidgetState.CLOSED)
    val searchWidgetState: State<SearchWidgetState> = _searchWidgetState

    private val _inputText = mutableStateOf("")
    val inputText: State<String> = _inputText

    private val _searchHistory = MutableStateFlow<List<String>>(emptyList())
    val searchHistory: StateFlow<List<String>> = _searchHistory

    fun onTextChanged(text: String) {
        _inputText.value = text
    }

    fun addSearchToHistory(searchText: String) {
        if (searchText.isNotEmpty() && !_searchHistory.value.contains(searchText)) {
            viewModelScope.launch(dispatcherProvider.io) {
                val newSearchList = _searchHistory.value.toMutableList()

                newSearchList.add(searchText)
                _searchHistory.emit(newSearchList.toList())
            }
        }
    }

    fun updateWidgetState(newState: SearchWidgetState) {
        _searchWidgetState.value = newState
    }

}

enum class SearchWidgetState {
    OPEN,
    CLOSED
}