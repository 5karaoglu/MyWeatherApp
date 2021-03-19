package com.example.myweatherapp.module

import android.content.Context
import androidx.room.Room
import com.example.myweatherapp.data.Api
import com.example.myweatherapp.data.WeatherDao
import com.example.myweatherapp.data.WeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Singleton
    @Provides
    fun provideApiService(): Api{
        return Retrofit.Builder()
            .baseUrl("http://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(@ApplicationContext context: Context): WeatherDatabase{
        return Room.databaseBuilder(context,
        WeatherDatabase::class.java,
        WeatherDatabase.DB_NAME).build()
    }

    @Singleton
    @Provides
    fun provideDao(database: WeatherDatabase):WeatherDao{
        return database.dao()
    }
}