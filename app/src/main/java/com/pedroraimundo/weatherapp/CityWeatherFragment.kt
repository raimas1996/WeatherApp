package com.pedroraimundo.weatherapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import org.w3c.dom.Text

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "cityName"
private const val ARG_PARAM2 = "isLocationActive"
private const val ARG_PARAM3 = "lat"
private const val ARG_PARAM4 = "lon"

/**
 * A simple [Fragment] subclass.
 * Use the [CityWeatherFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CityWeatherFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: Boolean? = null
    private var param3: Double? = null
    private var param4: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getBoolean(ARG_PARAM2)
            param3 = it.getDouble(ARG_PARAM3)
            param4 = it.getDouble(ARG_PARAM4)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_city_weather, container, false)

        var listView = rootView.findViewById<ListView>(R.id.list_details)

        if (param2 == true) {
            JSONDownloader(
                context!!,
                "http://api.openweathermap.org/data/2.5/weather?lat=$param3&lon=$param4&appid=9a4353bae9504437ea2ee3b6b4d89b17", listView
            ).execute()
        } else {
            JSONDownloader(
                context!!,
                "http://api.openweathermap.org/data/2.5/weather?q=$param1&appid=9a4353bae9504437ea2ee3b6b4d89b17", listView
            ).execute()
        }

        return rootView
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment CityWeatherFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: Boolean, param3: Double, param4: Double) =
            CityWeatherFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putBoolean(ARG_PARAM2, param2)
                    putDouble(ARG_PARAM3, param3)
                    putDouble(ARG_PARAM4, param4)
                }
            }
    }
}