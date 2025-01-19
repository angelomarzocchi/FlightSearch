package com.example.flightsearch.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "airport", indices = [Index(value = ["iata_code"], unique = true)] )
data class Airport(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "iata_code")
    val code: String,
    val name: String,
    val passengers: Int
)
