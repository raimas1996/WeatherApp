package com.pedroraimundo.weatherapp

import android.Manifest
import android.app.ProgressDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.*
import com.pedroraimundo.adapters.CityListAdapter
import com.pedroraimundo.entities.City


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CityListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@Suppress("DEPRECATION")
class CityListFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var locationManager: LocationManager
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val locationPermissionCode = 2
    var lat = 0.0
    var lon = 0.0
    var isLocationActive = false
    private lateinit var pd: ProgressDialog

    var arrayOfItems = ArrayList<City?>()
    private lateinit var cityList: ListView
    private var adapter: CityListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_city_list, container, false)

        getLocation()

        val citiesArray = arrayOf(
                getString(R.string.your_location),
                getString(R.string.lisbon),
                getString(R.string.madrid),
                getString(R.string.paris),
                getString(R.string.berlin),
                getString(R.string.copenhagen),
                getString(R.string.rome),
                getString(R.string.london),
                getString(R.string.dublin),
                getString(R.string.prague),
                getString(R.string.vienna),
        )

        cityList = rootView.findViewById(R.id.city_list)

        adapter = CityListAdapter(context, arrayOfItems)

        if (adapter!!.isEmpty) {
            for (city in citiesArray) {
                adapter!!.add(City(city))
            }
        }

        cityList.adapter = adapter

        cityList.onItemClickListener = AdapterView.OnItemClickListener { parent, view, i, id ->
            if (i == 0 && !isLocationActive) {
                getLocation()
            } else {
                newFragmentFunction(i)
            }
        }

        return rootView
    }

    private fun newFragmentFunction(i: Int) {
        val newFragment = CityWeatherFragment.newInstance(adapter!!.getItem(i)!!.cityName, isLocationActive, lat, lon)
        isLocationActive = false

        val transaction = fragmentManager!!.beginTransaction()
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container, newFragment)
        transaction.addToBackStack(null)
        // Commit the transaction
        transaction.commit()
    }

    private fun getLocation() {
        locationManager = context!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)
        if (ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions

            ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
        fusedLocationClient.lastLocation.addOnSuccessListener {
            pd = ProgressDialog(context)
            pd.setTitle("Obtaining GPS Coordinates")
            pd.setMessage("Please wait...")
            pd.show()
            isLocationActive = true
            // Got last known location. In some rare situations this can be null.
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f) { location: Location ->
                lat = location.latitude
                lon = location.longitude
                pd.dismiss()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Toast.makeText(context!!, "Permission Granted", Toast.LENGTH_SHORT).show()
            }
            else {
                //Toast.makeText(context!!, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CityListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CityListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}