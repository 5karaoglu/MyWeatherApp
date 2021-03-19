package com.example.myweatherapp.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myweatherapp.*
import com.example.myweatherapp.R
import com.example.myweatherapp.data.Weather
import com.example.myweatherapp.util.CustomAdapter
import com.example.myweatherapp.util.CustomItemDecoration
import com.example.myweatherapp.util.CustomOnClickListener
import com.example.myweatherapp.util.DataState
import com.google.android.gms.location.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*


@AndroidEntryPoint
class MainFragment :
    Fragment(R.layout.fragment_main),
        CustomOnClickListener {

    private val TAG = "MainFragment"
    private val ITEM_SPACE = 20
    private val REQUEST_CODE_LOCATION_PERMISSION = 1
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val viewModel: MainViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        val recyclerView = requireActivity().findViewById<RecyclerView>(R.id.recycler)
        val adapter = CustomAdapter(this,requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(CustomItemDecoration(ITEM_SPACE))

        viewModel.dataState.observe(viewLifecycleOwner, {
            //do recyclerview
            Log.d(TAG, "onViewCreated: sup")
            when (it) {
                is DataState.Success<*> -> {
                    val list: List<Weather> = it.data as List<Weather>
                    pb.visibility = View.GONE
                    for (i in list) {
                        Log.d(TAG, "onViewCreated: ${i.name}")
                    }
                    adapter.submitList(list)
                    Log.d(TAG, "onViewCreated: success")
                }
                is DataState.Error -> {
                    pb.visibility = View.GONE
                    Log.d(TAG, it.e.message.toString())
                }
                is DataState.Loading -> {
                    pb.visibility = View.VISIBLE
                    Log.d(TAG, "onViewCreated: loading ")
                }
            }
        })


        buttonAdd.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_addFragment)
        }
        buttonDeleteAll.setOnClickListener {
            deleteAll()
        }
        buttonLocation.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        REQUEST_CODE_LOCATION_PERMISSION)
            }else{
                getCurrentLocation()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.setStateEvent(CustomStateEvent.GetWeathers,null,null,null)
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(){
        fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY,null).addOnCompleteListener {
            if (it.isSuccessful){
                viewModel.setStateEvent(CustomStateEvent.GetCityWeatherWithCoord,null,it.result.latitude,it.result.longitude)
            }else{
                Toast.makeText(requireContext(), "Error getting location! ${it.exception!!.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteAll(){
        AlertDialog.Builder(requireContext())
                .setCancelable(false)
                .setTitle("DELETE ALL")
                .setMessage("Are you sure you want to delete all items ?")
                .setPositiveButton("YES", DialogInterface.OnClickListener { dialog, which ->
                    viewModel.setStateEvent(CustomStateEvent.DeleteAllCities,null,null,null)
                })
                .setNegativeButton("CANCEL",null)
                .create()
                .show()
    }

    override fun onItemClicked(currentWeather: Weather) {
        val bundle = Bundle()

        //Coord
        bundle.putDouble("lon",currentWeather.coord.lon)
        bundle.putDouble("lat",currentWeather.coord.lat)
        //WeatherElement
        bundle.putLong("we_id",currentWeather.weather[0].id)
        bundle.putString("main",currentWeather.weather[0].main)
        bundle.putString("description",currentWeather.weather[0].description)
        bundle.putString("icon",currentWeather.weather[0].icon)
        //
        bundle.putString("base",currentWeather.base)
        //Main
        bundle.putDouble("temp",currentWeather.main.temp)
        bundle.putDouble("temp_min",currentWeather.main.tempMin)
        bundle.putDouble("temp_max",currentWeather.main.tempMax)
        bundle.putDouble("feels_like",currentWeather.main.feelsLike)
        bundle.putLong("pressure",currentWeather.main.pressure)
        bundle.putLong("humidity",currentWeather.main.humidity)
        //
        bundle.putLong("visibility",currentWeather.visibility)
        //Wind
        bundle.putDouble("wind_speed",currentWeather.wind.speed)
        bundle.putLong("wind_degree",currentWeather.wind.deg)
        //Clouds
        bundle.putLong("all",currentWeather.clouds.all)
        //
        bundle.putLong("dt",currentWeather.dt)
        //Sys
        bundle.putLong("type",currentWeather.sys.type)
        bundle.putLong("sys_id",currentWeather.sys.id)
        bundle.putString("country",currentWeather.sys.country)
        bundle.putLong("sunrise",currentWeather.sys.sunrise)
        bundle.putLong("sunset",currentWeather.sys.sunset)
        //
        bundle.putLong("timezone",currentWeather.timezone)
        //
        bundle.putLong("id",currentWeather.id)
        //
        bundle.putString("name",currentWeather.name)
        bundle.putLong("cod",currentWeather.cod)

        findNavController().navigate(R.id.action_mainFragment_to_inspectFragment,bundle)
    }

    override fun onItemLongClicked(currentWeather: Weather): Boolean {
        AlertDialog.Builder(requireContext())
                .setCancelable(false)
                .setTitle("DELETE ${currentWeather.name}")
                .setMessage("Are you sure you want to delete ?")
                .setPositiveButton("YES", DialogInterface.OnClickListener { dialog, which ->
                    Log.d(TAG, "onItemLongClicked: ${currentWeather.name}")
                    viewModel.setStateEvent(CustomStateEvent.DeleteCity,currentWeather.name,null,null)
                })
                .setNegativeButton("CANCEL",null)
                .create()
                .show()
        return true
    }


}