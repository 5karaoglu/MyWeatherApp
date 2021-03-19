package com.example.myweatherapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_city")
data class City (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "city")
    val city: String
    )
