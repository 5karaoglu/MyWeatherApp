package com.example.myweatherapp.util

import com.example.myweatherapp.data.*
import javax.inject.Inject

class CustomMapper
@Inject constructor(){

    //from api to model
    fun networkToWeather(weatherNetworkEntity: WeatherNetworkEntity):Weather{
        return Weather(
            coord = Coord(weatherNetworkEntity.coord.lon,weatherNetworkEntity.coord.lat),
            weather =  weatherNetworkMapper(weatherNetworkEntity.weather),
            base = weatherNetworkEntity.base,
            main = Main(weatherNetworkEntity.main.temp,weatherNetworkEntity.main.feelsLike,weatherNetworkEntity.main.tempMin,
                weatherNetworkEntity.main.tempMax,weatherNetworkEntity.main.pressure,weatherNetworkEntity.main.humidity),
            visibility = weatherNetworkEntity.visibility,
            wind = Wind(weatherNetworkEntity.wind.speed,weatherNetworkEntity.wind.deg),
            clouds = Clouds(weatherNetworkEntity.clouds.all),
            dt = weatherNetworkEntity.dt,
            sys = Sys(weatherNetworkEntity.sys.type,weatherNetworkEntity.sys.uid,
                    weatherNetworkEntity.sys.country,weatherNetworkEntity.sys.sunrise,
                weatherNetworkEntity.sys.sunset),
            timezone = weatherNetworkEntity.timezone,
            id = weatherNetworkEntity.uid,
            name = weatherNetworkEntity.name,
            cod = weatherNetworkEntity.cod
        )
    }
    //from model to db model
    fun mapperToCacheWeather(weather: Weather):WeatherCacheEntity{
        return WeatherCacheEntity(
                //coord
                lon = weather.coord.lon,
                lat = weather.coord.lat,
                //weather element
                weatherElementId = weather.weather[0].id,
                main = weather.weather[0].main,
                description = weather.weather[0].description,
                uicon = weather.weather[0].icon,

                base = weather.base,
                //main
                temp = weather.main.temp,
                feelsLike = weather.main.feelsLike,
                tempMin = weather.main.tempMin,
                tempMax = weather.main.tempMax,
                pressure = weather.main.pressure,
                humidity = weather.main.humidity,

                visibility = weather.visibility,
                //wind
                speed = weather.wind.speed,
                deg = weather.wind.deg,
                //clouds
                all = weather.clouds.all,

                dt = weather.dt,
                //sys
                type = weather.sys.type,
                sysId = weather.sys.id,
                country = weather.sys.country,
                sunrise = weather.sys.sunrise,
                sunset = weather.sys.sunset,


                timezone = weather.timezone,
                uid = weather.id,
                name = weather.name,
                cod = weather.cod
        )
    }


    //from db model to model
    fun cacheToMapper(weatherCacheEntity: WeatherCacheEntity, ):Weather{
        return Weather(
            coord = Coord(weatherCacheEntity.lon,weatherCacheEntity.lat),
            weather = listOf(WeatherElement(weatherCacheEntity.weatherElementId,weatherCacheEntity.main,
                    weatherCacheEntity.description,weatherCacheEntity.uicon)),
            base = weatherCacheEntity.base,
            main = Main(weatherCacheEntity.temp,weatherCacheEntity.feelsLike,weatherCacheEntity.tempMin,
            weatherCacheEntity.tempMax,weatherCacheEntity.pressure,weatherCacheEntity.humidity),
            visibility = weatherCacheEntity.visibility,
            wind = Wind(weatherCacheEntity.speed,weatherCacheEntity.deg),
            clouds = Clouds(weatherCacheEntity.all),
            dt = weatherCacheEntity.dt,
            sys = Sys(weatherCacheEntity.type,weatherCacheEntity.sysId,weatherCacheEntity.country,
                weatherCacheEntity.sunrise,weatherCacheEntity.sunset),
            timezone = weatherCacheEntity.timezone,
            id = weatherCacheEntity.uid,
            name = weatherCacheEntity.name,
            cod = weatherCacheEntity.cod
        )
    }

    fun mapFromList(entities:List<WeatherCacheEntity>):List<Weather>{
        return entities.map { cacheToMapper(it) }
    }


    fun weatherNetworkMapper(elementNetworkEntity: List<WeatherElementNetworkEntity>):List<WeatherElement>{
        var list = mutableListOf<WeatherElement>()
        for (i in elementNetworkEntity){
            list.add(WeatherElement(i.uid,i.main,i.description,i.uicon))
        }
        return list
    }

}