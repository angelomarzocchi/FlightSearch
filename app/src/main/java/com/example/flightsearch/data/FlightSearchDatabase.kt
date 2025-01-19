package com.example.flightsearch.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.flightsearch.data.dao.AirportDao
import com.example.flightsearch.data.dao.FavoriteRouteDao
import com.example.flightsearch.data.entity.Airport
import com.example.flightsearch.data.entity.Favorite

@Database(entities = [Airport::class, Favorite::class], version = 1, exportSchema = false)
abstract class FlightSearchDatabase: RoomDatabase() {

    abstract fun airportDao(): AirportDao
    abstract fun favoriteRouteDao(): FavoriteRouteDao

    companion object {
        @Volatile
        private var Instance: FlightSearchDatabase? = null

        fun getDatabase(context: Context): FlightSearchDatabase {
            return Instance ?: synchronized(this) {
                return Instance ?: Room.databaseBuilder(
                    context,
                    FlightSearchDatabase::class.java,
                    "airport_search_database"
                )
                  //  .createFromAsset("database/flight_search.db")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}