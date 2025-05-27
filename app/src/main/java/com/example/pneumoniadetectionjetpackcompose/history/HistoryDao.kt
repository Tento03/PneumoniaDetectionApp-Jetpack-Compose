package com.example.pneumoniadetectionjetpackcompose.history

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.pneumoniadetectionjetpackcompose.model.History
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Insert
    fun addHistory(history: History)

    @Query("SELECT * FROM history")
    fun getHistory(): Flow<List<History>>

    @Query("SELECT * FROM history WHERE id=:id")
    fun getHistoryId(id: String): Flow<History>

    @Query("SELECT * FROM history WHERE title LIKE:query OR user LIKE:query")
    fun searchHistory(query: String): Flow<List<History>>

    @Update
    fun updateHistory(history: History)

    @Query("DELETE FROM history WHERE id=:id")
    fun deleteHistory(id: String)
}