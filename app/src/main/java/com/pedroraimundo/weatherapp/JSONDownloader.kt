package com.pedroraimundo.weatherapp

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.view.View
import android.widget.ListView
import android.widget.Toast
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

@Suppress("DEPRECATION")
class JSONDownloader(private var context: Context, private var jsonURL: String, private var myListView: ListView) : AsyncTask<Void, Void, String>() {

    private lateinit var pd: ProgressDialog

    /*
   Connect to network via HTTPURLConnection
    */
    private fun connect(jsonURL: String): Any {
        try {
            val url = URL(jsonURL)
            val con = url.openConnection() as HttpURLConnection

            //CON PROPS
            con.requestMethod = "GET"
            con.connectTimeout = 15000
            con.readTimeout = 15000
            con.doInput = true

            return con

        } catch (e: MalformedURLException) {
            e.printStackTrace()
            return "URL ERROR " + e.message

        } catch (e: IOException) {
            e.printStackTrace()
            return "CONNECT ERROR " + e.message
        }
    }
    /*
    Download JSON data
     */
    private fun download(): String {
        val connection = connect(jsonURL)
        if (connection.toString().startsWith("Error")) {
            return connection.toString()
        }
        //DOWNLOAD
        try {
            val con = connection as HttpURLConnection
            //if response is HTTP OK
            if (con.responseCode == 200) {
                //GET INPUT FROM STREAM
                val `is` = BufferedInputStream(con.inputStream)
                val br = BufferedReader(InputStreamReader(`is`))

                var jsonData = StringBuffer()
                var line: String?

                do {
                    line = br.readLine()

                    if (line == null){ break}

                    jsonData.append(line + "\n");

                } while (true)

                //CLOSE RESOURCES
                br.close()
                `is`.close()

                //RETURN JSON
                return jsonData.toString()

            } else {
                return "Error " + con.responseMessage
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return "Error " + e.message
        }
    }
    /*
    show dialog while downloading data
     */
    override fun onPreExecute() {
        super.onPreExecute()

        //pd = ProgressDialog(context)
        //pd.setTitle("Download JSON")
        //pd.setMessage("Downloading...Please wait")
        //pd.show()
    }
    /*
    Perform Background downloading of data
     */
    override fun doInBackground(vararg voids: Void): String {
        return download()
    }
    /*
    When BackgroundWork Finishes, dismiss dialog and pass data to JSONParser
     */
    override fun onPostExecute(jsonData: String) {
        super.onPostExecute(jsonData)

        //pd.dismiss()
        when {
            jsonData.startsWith("URL ERROR") -> {
                //Toast.makeText(context, jsonData, Toast.LENGTH_LONG).show()
                //Toast.makeText(context, "MOST PROBABLY APP CANNOT CONNECT DUE TO WRONG URL SINCE MALFORMEDURLEXCEPTION WAS RAISED", Toast.LENGTH_LONG).show()

            }
            jsonData.startsWith("CONNECT ERROR") -> {
                //Toast.makeText(context, jsonData, Toast.LENGTH_LONG).show()
                //Toast.makeText(context, "MOST PROBABLY APP CANNOT CONNECT TO ANY NETWORK SINCE IOEXCEPTION WAS RAISED", Toast.LENGTH_LONG).show()
            }
            else -> {
                //PARSE
                //Toast.makeText(context, "Network Connection and Download Successful. Now attempting to parse .....", Toast.LENGTH_LONG).show()
                JSONParser(context, jsonData, myListView).execute()
            }
        }
    }

}