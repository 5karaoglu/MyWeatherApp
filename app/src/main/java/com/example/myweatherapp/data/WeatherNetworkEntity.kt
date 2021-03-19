package com.example.myweatherapp.data


import com.google.gson.annotations.SerializedName


data class WeatherNetworkEntity (

    @SerializedName("coord")
    val coord: CoordNetworkEntity,

    @SerializedName("weather")
    val weather: List<WeatherElementNetworkEntity>,

    @SerializedName("base")
    val base: String,

    @SerializedName("main")
    val main: MainNetworkEntity,

    @SerializedName("visibility")
    val visibility: Long,

    @SerializedName("wind")
    val wind: WindNetworkEntity,

    @SerializedName("clouds")
    val clouds: CloudsNetworkEntity,

    @SerializedName("dt")
    val dt: Long,

    @SerializedName("sys")
    val sys: SysNetworkEntity,

    @SerializedName("timezone")
    val timezone: Long,

    @SerializedName("id")
    val uid: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("cod")
    val cod: Long

)

data class CloudsNetworkEntity (

    @SerializedName("all")
    val all: Long
)

data class CoordNetworkEntity (

    @SerializedName("lon")
    val lon: Double,

    @SerializedName("lat")
    val lat: Double
)

data class MainNetworkEntity (

    @SerializedName("temp")
    val temp: Double,

    @SerializedName("feels_like")
    val feelsLike: Double,

    @SerializedName("temp_min")
    val tempMin: Double,

    @SerializedName("temp_max")
    val tempMax: Double,

    @SerializedName("pressure")
    val pressure: Long,

    @SerializedName("humidity")
    val humidity: Long
)

data class SysNetworkEntity (

    @SerializedName("type")
    val type: Long,

    @SerializedName("id")
    val uid: Long,

    @SerializedName("country")
    val country: String,

    @SerializedName("sunrise")
    val sunrise: Long,

    @SerializedName("sunset")
    val sunset: Long
)

data class WeatherElementNetworkEntity (

    @SerializedName("id")
    val uid: Long,

    @SerializedName("main")
    val main: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("icon")
    val uicon: String
)

data class WindNetworkEntity (

    @SerializedName("speed")
    val speed: Double,

    @SerializedName("deg")
    val deg: Long
)