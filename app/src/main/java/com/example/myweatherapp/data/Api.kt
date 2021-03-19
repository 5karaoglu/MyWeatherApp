package com.example.myweatherapp.data

import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("weather?appid=9837cc71d01fda171684e3460c35e542&units=metric")
    suspend fun getWeather(@Query("q") city: String,): WeatherNetworkEntity

    @GET("weather?appid=9837cc71d01fda171684e3460c35e542&units=metric")
    suspend fun getWeatherWithCoord(@Query("lat") lat: Double,@Query("lon") lon: Double): WeatherNetworkEntity
}