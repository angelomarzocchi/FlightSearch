package com.example.flightsearch.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.flightsearch.data.entity.Favorite
import com.example.flightsearch.data.entity.FavoriteRoute
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteRouteDao {

    @Query("SELECT f.id as favorite_id,f.departure_code,f.destination_code, a.name as departureName, b.name as destinationName FROM favorite as f JOIN airport as a ON f.departure_code = a.iata_code JOIN airport as b ON f.destination_code = b.iata_code")
     fun getAllFavoriteRoutes(): Flow<List<FavoriteRoute>>

    @Query("INSERT INTO favorite (departure_code, destination_code) VALUES (:departureCode, :destinationCode)")
     fun insertFavoriteRoute(departureCode: String, destinationCode: String)

     @Query("DELETE FROM favorite WHERE departure_code = :departureCode AND destination_code = :destinationCode")
     fun deleteFavoriteRoute(departureCode: String, destinationCode: String)

     @Query("DELETE FROM favorite WHERE id = :favoriteId")
     fun deleteFavoriteRoute(favoriteId: Int)

}