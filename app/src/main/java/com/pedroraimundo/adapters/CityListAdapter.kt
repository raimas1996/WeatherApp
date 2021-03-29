package com.pedroraimundo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.pedroraimundo.entities.City
import com.pedroraimundo.entities.WeatherResponse
import com.pedroraimundo.weatherapp.R
import java.util.ArrayList

class CityListAdapter(context: Context?, items: ArrayList<City?>) : ArrayAdapter<City?>(context!!, 0, items){

    override fun getView(i: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val city = getItem(i)

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.city, parent, false)
        }

        val cityName = convertView?.findViewById<TextView>(R.id.city_name)
        cityName?.text = city!!.cityName

        return convertView!!
    }
}