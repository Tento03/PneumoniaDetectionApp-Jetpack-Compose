package com.example.pneumoniadetectionjetpackcompose.history

import com.example.pneumoniadetectionjetpackcompose.model.History
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HistoryRepository @Inject constructor(private var dao: HistoryDao) {
    suspend fun addHistory(history: History) {
        return dao.addHistory(history)
    }

    suspend fun getHistory(): Flow<List<History>> {
        return dao.getHistory()
    }

    suspend fun getHistoryId(id: String): Flow<History> {
        return dao.getHistoryId(id)
    }

    suspend fun searchHistory(query: String): Flow<List<History>> {
        return dao.searchHistory(query)
    }

    suspend fun updateHistory(history: History) {
        return dao.updateHistory(history)
    }

    suspend fun deleteHistory(id: String) {
        return dao.deleteHistory(id)
    }
}