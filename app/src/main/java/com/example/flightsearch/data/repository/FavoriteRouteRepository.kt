package com.example.flightsearch.data.repository

import com.example.flightsearch.data.entity.Favorite
import com.example.flightsearch.data.entity.FavoriteRoute
import kotlinx.coroutines.flow.Flow

interface FavoriteRouteRepository {
     fun getAllFavoriteRoutes(): Flow<List<FavoriteRoute>>
    suspend fun insertFavoriteRoute(departureCode: String, destinationCode: String)
    suspend fun deleteFavoriteRoute(departureCode: String, destinationCode: String)
    suspend fun deleteFavoriteRoute(favoriteRoute: FavoriteRoute)

}