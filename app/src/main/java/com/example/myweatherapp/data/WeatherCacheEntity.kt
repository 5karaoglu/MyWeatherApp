package com.example.myweatherapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "table_weather")
data class WeatherCacheEntity (

        //coord
        @ColumnInfo(name = "lon")
        val lon: Double,
        @ColumnInfo(name = "lat")
        val lat: Double,

        //weather element
        @ColumnInfo(name = "weather_element_id")
        val weatherElementId: Long,
        @ColumnInfo(name = "main")
        val main: String,
        @ColumnInfo(name = "description")
        val description: String,
        @ColumnInfo(name = "uicon")
        val uicon: String,

        @ColumnInfo(name = "base")
        val base: String,

        //main
        @ColumnInfo(name = "temp")
        val temp: Double,
        @ColumnInfo(name = "feels_like")
        val feelsLike: Double,
        @ColumnInfo(name = "temp_min")
        val tempMin: Double,
        @ColumnInfo(name = "temp_max")
        val tempMax: Double,
        @ColumnInfo(name = "pressure")
        val pressure: Long,
        @ColumnInfo(name = "humidity")
        val humidity: Long,

        @ColumnInfo(name = "visibility")
        val visibility: Long,

        //wind
        @ColumnInfo(name = "speed")
        val speed: Double,
        @ColumnInfo(name = "deg")
        val deg: Long,

        //clouds
        @ColumnInfo(name = "all")
        val all: Long,

        @ColumnInfo(name = "dt")
        val dt: Long,

        //sys
        @ColumnInfo(name = "type")
        val type: Long,
        @ColumnInfo(name = "sys_id")
        val sysId: Long,
        @ColumnInfo(name = "country")
        val country: String,
        @ColumnInfo(name = "sunrise")
        val sunrise: Long,
        @ColumnInfo(name = "sunset")
        val sunset: Long,

        @ColumnInfo(name = "timezone")
        val timezone: Long,

        @ColumnInfo(name = "uid")
        val uid: Long,

        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = "name")
        val name: String,

        @ColumnInfo(name = "cod")
        val cod: Long
)
