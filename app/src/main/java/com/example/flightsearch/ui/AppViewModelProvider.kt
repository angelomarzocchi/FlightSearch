package com.example.flightsearch.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearch.FlightSearchApplication


const val TIMEOUT_MILLIS = 5_000L
object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            AirportViewModel(
                flightSearchApplication().container.airportRepository,
                flightSearchApplication().container.favoriteRouteRepository
            )
        }

        initializer {
            FavoriteRouteViewModel(
                flightSearchApplication().container.favoriteRouteRepository
            )
        }
    }

}

fun CreationExtras.flightSearchApplication(): FlightSearchApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FlightSearchApplication)