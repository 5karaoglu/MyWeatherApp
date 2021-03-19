package com.example.myweatherapp.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myweatherapp.R
import com.example.myweatherapp.data.Weather

class CustomAdapter(private val clickListener: CustomOnClickListener,
                    private val context:Context): ListAdapter<Weather, CustomAdapter.CustomViewHolder>(CustomComparator()) {

    class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val iv = itemView.findViewById<ImageView>(R.id.imageView)
        private val tvHeader = itemView.findViewById<TextView>(R.id.tvHeader)
        private val tvDegree = itemView.findViewById<TextView>(R.id.tvDegree)
        private val tvDes = itemView.findViewById<TextView>(R.id.tvDes)


        fun bind(currentWeather: Weather, clickListener: CustomOnClickListener,
                 context:Context) {

            Constants.picassoBuilder(currentWeather.weather[0].icon,iv)
            tvHeader.text = currentWeather.name
            tvDegree.text = String.format(context.getString(R.string.temp_adapter),
                    currentWeather.main.temp.toInt())
            tvDes.text = currentWeather.weather[0].main

            itemView.setOnClickListener {
                clickListener.onItemClicked(currentWeather)
            }
            itemView.setOnLongClickListener {
                clickListener.onItemLongClicked(currentWeather)
            }
        }
    }
    companion object {
        fun create(parent: ViewGroup): CustomViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.weather_item,parent,false)
            return CustomViewHolder(view)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return create(parent)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val currentWeather = getItem(position)
        holder.bind(currentWeather,clickListener,context)
    }

}
interface CustomOnClickListener{
    fun onItemClicked(currentWeather: Weather)
    fun onItemLongClicked(currentWeather: Weather):Boolean
}


class CustomComparator(): DiffUtil.ItemCallback<Weather>(){
    override fun areItemsTheSame(oldItem: Weather, newItem: Weather): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Weather, newItem: Weather): Boolean {
        return oldItem.name == newItem.name
    }

}
