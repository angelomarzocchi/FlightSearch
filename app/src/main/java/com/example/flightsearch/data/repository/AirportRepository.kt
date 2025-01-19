package com.example.flightsearch.data.repository

import com.example.flightsearch.data.entity.Airport
import kotlinx.coroutines.flow.Flow

interface AirportRepository {
     fun getAirportByExactCodeOrName(query: String): Flow<List<Airport>>
     fun getArrivalAirportList(airportId: Int): Flow<List<Airport>>
}