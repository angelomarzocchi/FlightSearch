package com.example.flightsearch.data.repository

import com.example.flightsearch.data.dao.FavoriteRouteDao
import com.example.flightsearch.data.entity.Favorite
import com.example.flightsearch.data.entity.FavoriteRoute
import com.example.flightsearch.ui.FavoriteRouteUiState
import com.example.flightsearch.ui.common.Route
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class FavoriteRouteUiState(
    val potentialRoutes: List<Route> = listOf()
)

class FavoriteRouteRepositoryImpl(private val favoriteRouteDao: FavoriteRouteDao) : FavoriteRouteRepository {
    override  fun getAllFavoriteRoutes(): Flow<List<FavoriteRoute>> =
        favoriteRouteDao.getAllFavoriteRoutes()

    override suspend fun insertFavoriteRoute(departureCode: String, destinationCode: String) =
        favoriteRouteDao.insertFavoriteRoute(departureCode, destinationCode)

    override suspend fun deleteFavoriteRoute(departureCode: String, destinationCode: String) =
        favoriteRouteDao.deleteFavoriteRoute(departureCode, destinationCode)

    override suspend fun deleteFavoriteRoute(favoriteRoute: FavoriteRoute) =
        favoriteRouteDao.deleteFavoriteRoute(favoriteRoute.favoriteId)




}