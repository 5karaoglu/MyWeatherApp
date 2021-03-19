package com.example.myweatherapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweatherapp.data.Weather
import com.example.myweatherapp.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
    private val repository: Repository
): ViewModel(){

    private val TAG = "MainViewModel"

    private val _dataState : MutableLiveData<DataState<List<Weather>>> = MutableLiveData()
    val dataState : LiveData<DataState<List<Weather>>>
        get() = _dataState

    private val _cities : MutableLiveData<List<String>> = MutableLiveData()
    val cities : LiveData<List<String>>
        get() = _cities

    private val _currentStatus : MutableLiveData<String> = MutableLiveData()
    val currentStatus : LiveData<String>
        get() = _currentStatus


    private fun launchDataLoad(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                block()
                _currentStatus.value = Repository.SUCCESS_CODE.toString()
            }catch (e:CustomError){
                _currentStatus.value = e.message
            }
        }
    }

    fun setStateEvent(customStateEvent: CustomStateEvent, city: String?, lat: Double?, lon: Double?) {
        when(customStateEvent){
            is CustomStateEvent.GetWeathers -> viewModelScope.launch {
                Log.d(TAG, "setStateEvent: things happening")
                repository.getWeathers()
                        .onEach { dataState -> _dataState.value = dataState }
                        .catch { cause -> _dataState.value = DataState.Error(cause) }
                        .launchIn(viewModelScope)

            }
            is CustomStateEvent.GetCityWeather -> launchDataLoad { city?.let { repository.getCityWeather(it) } }

            is CustomStateEvent.DeleteAllCities ->
                viewModelScope.launch {
                repository.deleteAllCities()
                    setStateEvent(CustomStateEvent.GetWeathers,null,null,null)
            }

            is CustomStateEvent.DeleteCity ->
                viewModelScope.launch {
                    city?.let { repository.deleteCity(it) }
                    setStateEvent(CustomStateEvent.GetWeathers,null,null,null)
                }
            is CustomStateEvent.GetCityWeatherWithCoord ->
                viewModelScope.launch {
                    withContext(Dispatchers.Default) { lat?.let {lat -> lon?.let { lon -> repository.getCityWeatherWithCoord(lat, lon) } } }
                    setStateEvent(CustomStateEvent.GetWeathers,null,null,null)
                }
            is CustomStateEvent.InsertCity ->
                viewModelScope.launch {
                city?.let { repository.insertCity(it) }
            }
            is CustomStateEvent.None -> Log.d(TAG, "setStateEvent: sup")
        }
    }



}


sealed class CustomStateEvent{

    object GetWeathers: CustomStateEvent()

    object GetCityWeather : CustomStateEvent()

    object GetCityWeatherWithCoord : CustomStateEvent()

    object InsertCity : CustomStateEvent()

    object DeleteCity : CustomStateEvent()

    object DeleteAllCities : CustomStateEvent()

    object None: CustomStateEvent()
}