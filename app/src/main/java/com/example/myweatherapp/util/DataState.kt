package com.example.myweatherapp.util

import java.lang.Exception

sealed class DataState<out R>{

    data class Success<out T>(val data: T): DataState<T>()

    data class Error(val e: Throwable): DataState<Nothing>()

    object Loading: DataState<Nothing>()
}