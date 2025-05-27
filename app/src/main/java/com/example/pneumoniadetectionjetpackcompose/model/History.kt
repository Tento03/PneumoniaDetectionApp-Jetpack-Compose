package com.example.pneumoniadetectionjetpackcompose.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class History(
    @PrimaryKey
    var id: String,
    var title: String,
    var user: String,
    var image: String,
    var date: String?
)