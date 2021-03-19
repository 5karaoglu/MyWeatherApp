package com.example.myweatherapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Database
(entities = [WeatherCacheEntity::class,City::class],version = 1,exportSchema = false)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun dao(): WeatherDao

    companion object{
        const val DB_NAME = "db_weather"
    }
}

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weatherCacheEntity: WeatherCacheEntity): Long

    @Delete
    suspend fun deleteCity(city:City):Int

    @Query("DELETE FROM table_city")
    suspend fun deleteAllCities():Int

    @Query("DELETE FROM table_weather")
    suspend fun deleteAllWeathers():Int

    @Delete
    suspend fun deleteWeather(weatherCacheEntity: WeatherCacheEntity):Int

    @Query("SELECT * FROM table_weather WHERE name= :name")
    suspend fun getWeather(name: String): WeatherCacheEntity

    @Query("SELECT * FROM table_weather")
    suspend fun getAll(): List<WeatherCacheEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCity(city: City): Long

    @Query("SELECT * FROM table_city")
    suspend fun getAllCities(): List<City>



}