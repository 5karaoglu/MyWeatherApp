package com.example.myweatherapp.ui

import android.os.Bundle
import android.provider.Settings.Global.getString
import android.provider.Settings.Secure.getString
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myweatherapp.MainViewModel
import com.example.myweatherapp.R
import com.example.myweatherapp.data.*
import com.example.myweatherapp.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_inspect.*

@AndroidEntryPoint
class InspectFragment : Fragment(R.layout.fragment_inspect) {

   private lateinit var weather : Weather
   private val viewModel: MainViewModel by viewModels()
   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      val bundle = requireArguments()
      getBundle(bundle)
      initValues(weather)

      buttonBack.setOnClickListener {
         findNavController().popBackStack()
      }
   }
   private fun getBundle(bundle: Bundle){
      bundle.let {
         weather = Weather(
                 Coord(bundle.getDouble("lat"),
                         bundle.getDouble("lon")),
                 listOf(WeatherElement(bundle.getLong("we_id"),
                         bundle.getString("main")!!,
                         bundle.getString("description")!!,
                         bundle.getString("icon")!!)),
                 bundle.getString("base")!!,
                 Main(
                         bundle.getDouble("temp"),
                         bundle.getDouble("feels_like"),
                         bundle.getDouble("temp_min"),
                         bundle.getDouble("temp_max"),
                         bundle.getLong("pressure"),
                         bundle.getLong("humidity")
                 ),
                 bundle.getLong("visibility"),
                 Wind( bundle.getDouble("wind_speed"),
                         bundle.getLong("wind_degree")),
                 Clouds(bundle.getLong("all")),
                 bundle.getLong("dt"),
                 Sys(bundle.getLong("type"),
                         bundle.getLong("sys_id"),
                         bundle.getString("country")!!,
                         bundle.getLong("sunrise"),
                         bundle.getLong("sunset")),
                 bundle.getLong("timezone"),
                 bundle.getLong("id"),
                 bundle.getString("name")!!,
                 bundle.getLong("cod")

         )
      }
   }

   private fun initValues(weather: Weather){
      //where values loaded
      tvCityName.text = weather.name
      tvDate.text = Constants.timeInMillisToDate(weather.dt)
      tvCityTemp.text = String.format(getString(R.string.temp),weather.main.temp.toString())
      tvMainDes.text = weather.weather[0].description
      Constants.picassoBuilder(weather.weather[0].icon,ivIcon)
      tvTempFeelsLike.text = String.format(getString(R.string.feels_like),weather.main.feelsLike.toString())
      tvTempMin.text = String.format(getString(R.string.temp_min),weather.main.tempMin.toString())
      tvTempMax.text = String.format(getString(R.string.temp_max),weather.main.tempMax.toString())
      tvTempPressure.text = String.format(getString(R.string.pressure),weather.main.pressure.toString())
      tvTempHumidity.text = String.format(getString(R.string.humidity),weather.main.humidity.toString())
      tvWindSpeed.text = String.format(getString(R.string.wind_speed),weather.wind.speed)
      tvWindDeg.text = String.format(getString(R.string.wind_deg),weather.wind.deg)
      tvCloudiness.text = String.format(getString(R.string.cloudiness),weather.clouds.all)
      tvSunrise.text = String.format(getString(R.string.sunrise),Constants.timeInMillisToHour(weather.sys.sunrise,weather.timezone))
      tvSunset.text = String.format(getString(R.string.sunset),Constants.timeInMillisToHour(weather.sys.sunset,weather.timezone))
      tvCountry.text = String.format(getString(R.string.country),weather.sys.country)
      tvUTC.text = String.format(getString(R.string.utc),Constants.secondsToHours(weather.timezone))
   }
}