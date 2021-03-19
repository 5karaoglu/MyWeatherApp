package com.example.myweatherapp

import android.util.Log
import com.example.myweatherapp.data.*
import com.example.myweatherapp.util.CustomMapper
import com.example.myweatherapp.util.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.Exception

class Repository
@Inject constructor(
    private val dao: WeatherDao,
    private val api: Api,
    private val customMapper: CustomMapper
){

    companion object{
        val SUCCESS_CODE = 200L
    }

    private val TAG = "Repository"

    suspend fun insertCity(city: String){
        dao.insertCity(City(city))
    }

    suspend fun deleteCity(city: String)= withContext(Dispatchers.IO){
       try {
           val del = async { dao.deleteCity(City(city)) }
           Log.d(TAG, "deleteCity: ${del.await()}")
           val weatherCache = async {
               dao.getWeather(city)
           }
           dao.deleteWeather(weatherCache.await())
           Log.d(TAG, "deleteCity: here")
       }catch (e:CustomError){
           throw CustomError(" Delete City Error",e)
       }
    }

    suspend fun deleteAllCities() = withContext(Dispatchers.IO){
        try {
            async { dao.deleteAllCities() }
            async { dao.deleteAllWeathers() }
        }catch (e:CustomError){
            throw CustomError("Error on deleting all cities!",e)
        }
    }.await()

    suspend fun getCityWeather(city:String)= withContext(Dispatchers.IO){
        try{
            val result = api.getWeather(city)
            if (result.cod == SUCCESS_CODE){
                dao.insertCity(City(result.name))
            }
        }catch (cause: CustomError){
            throw CustomError("Error!",cause)
        }
    }
    suspend fun getCityWeatherWithCoord(lat:Double, lon:Double)= withContext(Dispatchers.IO){
        try{
            val result = api.getWeatherWithCoord(lat,lon)
            if (result.cod == SUCCESS_CODE){
                dao.insertCity(City(result.name))
            }
        }catch (cause: CustomError){
            throw CustomError("Error!",cause)
        }
    }

    suspend fun getWeathers(): Flow<DataState<List<Weather>>> = flow {
        emit(DataState.Loading)

        try {
            Log.d(TAG, "getWeathers: i tried")
            val cities = dao.getAllCities()
            for (city in cities){
                Log.d(TAG, "getWeathers: ${city.city}")
                val weatherFromNetwork = api.getWeather(city.city)
                val toModel = customMapper.networkToWeather(weatherFromNetwork)
                dao.insertWeather(customMapper.mapperToCacheWeather(toModel))
            }
            val weather = dao.getAll()
            for (w in weather){
                Log.d(TAG, "getWeathers: ${w.name}")
            }
            emit(DataState.Success(customMapper.mapFromList(weather)))
        }catch (cause:CustomError){
            throw CustomError("Getting weathers failed!", cause)
        }
    }

}
class CustomError(message: String, cause: Throwable?): Throwable(message, cause)