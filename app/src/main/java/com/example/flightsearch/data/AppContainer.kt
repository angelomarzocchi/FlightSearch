package com.example.flightsearch.data

import android.content.Context
import com.example.flightsearch.data.repository.AirportRepository
import com.example.flightsearch.data.repository.AirportRepositoryImpl
import com.example.flightsearch.data.repository.FavoriteRouteRepository
import com.example.flightsearch.data.repository.FavoriteRouteRepositoryImpl

interface AppContainer {
    val airportRepository: AirportRepository
    val favoriteRouteRepository: FavoriteRouteRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val airportRepository: AirportRepository by lazy {
        AirportRepositoryImpl(FlightSearchDatabase.getDatabase(context).airportDao())
    }
    override val favoriteRouteRepository: FavoriteRouteRepository by lazy {
        FavoriteRouteRepositoryImpl(FlightSearchDatabase.getDatabase(context).favoriteRouteDao())
    }
}