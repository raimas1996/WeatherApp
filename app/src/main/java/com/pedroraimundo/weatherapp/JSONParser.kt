package com.pedroraimundo.weatherapp

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import com.pedroraimundo.adapters.WeatherDetailsAdapter
import com.pedroraimundo.entities.City
import com.pedroraimundo.entities.ForecastDetails
import com.pedroraimundo.entities.WeatherResponse
import com.pedroraimundo.entities.WindDetails
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*

@Suppress("DEPRECATION")

class JSONParser(private var context: Context, private var jsonData: String, private var myView: ListView) : AsyncTask<Void, Void, Boolean>() {

    private lateinit var pd: ProgressDialog
    private var weatherDetails = ArrayList<WeatherResponse?>()

    /*
    Parse JSON data
     */
    private fun parse(): Boolean {
        try {
            val ja = JSONArray("[$jsonData]")
            var jo: JSONObject

            weatherDetails.clear()
            var weather: WeatherResponse

            for (i in 0 until ja.length()) {
                jo = ja.getJSONObject(i)
                var icon = ""

                for (j in 0 until jo.getJSONArray("weather").length()) {
                    var jo2 = jo.getJSONArray("weather").getJSONObject(j)
                    icon = jo2.getString("icon")
                }

                val name = jo.getString("name")
                val forecast = ForecastDetails(jo.getJSONObject("main").getDouble("temp"),
                    jo.getJSONObject("main").getDouble("temp_min"),
                    jo.getJSONObject("main").getDouble("temp_max"),
                    jo.getJSONObject("main").getDouble("humidity"),
                    icon)
                val wind = WindDetails(jo.getJSONObject("wind").getDouble("speed"))

                weather = WeatherResponse(City(name), forecast, wind)
                weatherDetails.add(weather)
            }

            return true
        } catch (e: JSONException) {
            e.printStackTrace()
            return false
        }
    }
    override fun onPreExecute() {
        super.onPreExecute()

        //pd = ProgressDialog(context)
        //pd.setTitle("Parse JSON")
        //pd.setMessage("Parsing...Please wait")
        //pd.show()
    }

    override fun doInBackground(vararg voids: Void): Boolean? {
        return parse()
    }

    override fun onPostExecute(isParsed: Boolean?) {
        super.onPostExecute(isParsed)

        //pd.dismiss()
        if (isParsed!!) {
            //BIND
            myView.adapter = WeatherDetailsAdapter(context, weatherDetails)
        } else {
            //Toast.makeText(context, "Unable To Parse that data. ARE YOU SURE IT IS VALID JSON DATA? JsonException was raised. Check Log Output.", Toast.LENGTH_LONG).show()
            //Toast.makeText(context, "THIS IS THE DATA WE WERE TRYING TO PARSE :  "+ jsonData, Toast.LENGTH_LONG).show()
        }
    }

}