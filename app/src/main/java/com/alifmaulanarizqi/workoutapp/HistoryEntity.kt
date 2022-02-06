package com.alifmaulanarizqi.workoutapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: String = "",
    val cal: Double = 100.0,
    val time: String = "8"
)
