package com.example.myweatherapp.util

import android.util.Log
import android.widget.ImageView
import com.example.myweatherapp.R
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class Constants {
    companion object{

        fun secondsToHours(seconds:Long): String {
            var formatter = SimpleDateFormat("HH:mm")
            var calendar = Calendar.getInstance()
            calendar.timeInMillis = seconds
            Log.d("time", "timeInMillisToDate: ${formatter.format(calendar.time)}")
            return formatter.format(calendar.time)
        }
        fun timeInMillisToDate(tim:Long): String {
            var formatter = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z")
            var calendar = Calendar.getInstance()
            //our timestamp is seconds format
            calendar.timeInMillis = tim*1000
            Log.d("time", "timeInMillisToDate: ${formatter.format(calendar.time)}")
            return formatter.format(calendar.time)
        }

        fun timeInMillisToHour(tim:Long,timezone:Long): String {
            var formatter = SimpleDateFormat("HH:mm:ss ")
            var calendar = Calendar.getInstance()
            //adding timezone
            calendar.timeInMillis = tim*1000+timezone
            Log.d("time", "timeInMillisToDate: ${formatter.format(calendar.time)}")
            return formatter.format(calendar.time)
        }

        fun picassoBuilder(imageType:String, imageView: ImageView){
            Picasso.get()
                    .load("http://openweathermap.org/img/wn/$imageType@2x.png")
                    .error(R.drawable.ic_launcher_background)
                    .noPlaceholder()
                    .fit().centerInside()
                    .into(imageView)
        }

    }
}