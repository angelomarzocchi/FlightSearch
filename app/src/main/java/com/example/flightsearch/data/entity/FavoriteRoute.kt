package com.example.flightsearch.data.entity

import androidx.room.ColumnInfo

data class FavoriteRoute(
    @ColumnInfo(name = "favorite_id")
    val favoriteId: Int,
    @ColumnInfo(name = "departure_code")
    val departureCode: String,
    val departureName: String,
    @ColumnInfo(name = "destination_code")
    val destinationCode: String,
    val destinationName: String
)
