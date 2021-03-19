package com.example.myweatherapp.module

import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.myweatherapp.MainActivity
import com.example.myweatherapp.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object MainActivityModule {

    @Provides
    fun provideNavController(activity: MainActivity):NavController{
        return activity.findNavController(R.id.fragment_container)
    }

}