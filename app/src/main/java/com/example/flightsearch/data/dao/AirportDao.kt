package com.example.flightsearch.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.flightsearch.data.entity.Airport
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportDao {

    @Query("SELECT * FROM airport WHERE LOWER(iata_code) = LOWER(:query) OR LOWER(name) LIKE '%' || LOWER(:query) || '%' ORDER BY passengers DESC")
     fun getAirportByExactCodeOrName(query: String): Flow<List<Airport>>

     @Query("SELECT * FROM airport WHERE id != :airportId ORDER BY passengers DESC")
     fun getArrivalAirports(airportId: Int): Flow<List<Airport>>



}