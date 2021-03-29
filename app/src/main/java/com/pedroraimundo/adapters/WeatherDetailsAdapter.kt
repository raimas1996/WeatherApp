package com.pedroraimundo.adapters

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.pedroraimundo.entities.WeatherResponse
import com.pedroraimundo.weatherapp.R
import com.squareup.picasso.Picasso
import java.util.*

class WeatherDetailsAdapter(context: Context?, items: ArrayList<WeatherResponse?>) : ArrayAdapter<WeatherResponse?>(context!!, 0, items){

    @RequiresApi(Build.VERSION_CODES.N)
    override fun getView(i: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val city = getItem(i)

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.weather_details, parent, false)
        }

        val cityName = convertView?.findViewById<TextView>(R.id.city_name)
        cityName?.text = city!!.city.cityName + " - " + (city!!.forecast.currentTemp - 273.15).toInt().toString() + "ºC"

        val weatherIcon = convertView?.findViewById<ImageView>(R.id.icon_weather)
        Picasso.get().load("http://openweathermap.org/img/wn/" + city!!.forecast.icon + "@2x.png").into(weatherIcon)

        val maxTemp = convertView?.findViewById<TextView>(R.id.max_temp)
        maxTemp?.text = (city!!.forecast.maxTemp - 273.15).toInt().toString() + "ºC"

        val minTemp = convertView?.findViewById<TextView>(R.id.min_temp)
        minTemp?.text = (city!!.forecast.minTemp - 273.15).toInt().toString() + "ºC"

        val humidity = convertView?.findViewById<TextView>(R.id.humidity)
        humidity?.text = Html.fromHtml("<b>" + context!!.getString(R.string.humidity) + ":</b> " + city!!.forecast.humidity.toString() + "%")

        val windSpeed = convertView?.findViewById<TextView>(R.id.wind_speed)
        windSpeed?.text = Html.fromHtml("<b>" + context!!.getString(R.string.wind_speed) + ":</b> " + city!!.wind.windSpeed.toString() + " km/h")

        return convertView!!
    }
}