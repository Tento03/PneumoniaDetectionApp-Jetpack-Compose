package com.example.pneumoniadetectionjetpackcompose.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pneumoniadetectionjetpackcompose.model.History
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private var repository: HistoryRepository) :
    ViewModel() {
    private val _getHistory = MutableStateFlow<List<History>>(emptyList())
    var getHistory: MutableStateFlow<List<History>> = _getHistory

    private val _getHistoryId = MutableStateFlow<History?>(null)
    var getHistoryId: MutableStateFlow<History?> = _getHistoryId

    private val _searchHistory = MutableStateFlow<List<History>>(emptyList())
    var searchHistory: MutableStateFlow<List<History>> = _searchHistory

    init {
        getHistory()
    }

    fun addHistory(history: History) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                repository.addHistory(history)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getHistory() {
        try {
            viewModelScope.launch {
                repository.getHistory().collect {
                    _getHistory.value = it
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getHistoryId(id: String) {
        try {
            viewModelScope.launch {
                repository.getHistoryId(id).collect {
                    _getHistoryId.value = it
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun searchHistory(query: String) {
        try {
            viewModelScope.launch {
                repository.searchHistory(query).collect {
                    _getHistory.value = it
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun updateHistory(history: History) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                repository.updateHistory(history)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun deleteHistory(id: String) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                repository.deleteHistory(id)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}