package com.example.myweatherapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myweatherapp.CustomStateEvent
import com.example.myweatherapp.MainViewModel
import com.example.myweatherapp.R
import com.example.myweatherapp.Repository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add.*

@AndroidEntryPoint
class AddFragment :
    Fragment(R.layout.fragment_add) {

    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonBack.setOnClickListener {
           findNavController().popBackStack()
        }

        buttonAddCity.setOnClickListener {
            if (!etSearchCity.text.isNullOrEmpty()){
                viewModel.setStateEvent(CustomStateEvent.GetCityWeather,etSearchCity.text.toString(),null,null)
            }else{
                Toast.makeText(requireContext(), "Enter correct city!", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.currentStatus.observe(requireActivity(), {
            if (it == Repository.SUCCESS_CODE.toString()) {
                Toast.makeText(requireContext(), "City Added", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_addFragment_to_mainFragment)
            } else {
                Toast.makeText(requireContext(), "City couldn't added", Toast.LENGTH_SHORT).show()
            }
        })
    }

}