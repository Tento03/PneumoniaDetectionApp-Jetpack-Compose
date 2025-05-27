package com.example.pneumoniadetectionjetpackcompose.history

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pneumoniadetectionjetpackcompose.model.History

@Database(entities = [History::class], version = 1)
abstract class HistoryDB : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}