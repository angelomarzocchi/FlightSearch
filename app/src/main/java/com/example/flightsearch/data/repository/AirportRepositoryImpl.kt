package com.example.flightsearch.data.repository

import com.example.flightsearch.data.dao.AirportDao
import com.example.flightsearch.data.entity.Airport
import kotlinx.coroutines.flow.Flow

class AirportRepositoryImpl(private val airportDao: AirportDao) : AirportRepository {
    override  fun getAirportByExactCodeOrName(query: String): Flow<List<Airport>> =
        airportDao.getAirportByExactCodeOrName(query)

    override fun getArrivalAirportList(airportId: Int): Flow<List<Airport>> =
        airportDao.getArrivalAirports(airportId)


}