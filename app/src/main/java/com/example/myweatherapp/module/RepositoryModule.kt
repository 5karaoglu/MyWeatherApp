package com.example.myweatherapp.module

import com.example.myweatherapp.util.CustomMapper
import com.example.myweatherapp.Repository
import com.example.myweatherapp.data.Api
import com.example.myweatherapp.data.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        dao: WeatherDao,
        api: Api,
        customMapper: CustomMapper): Repository {
        return Repository(dao,api,customMapper)
    }
}