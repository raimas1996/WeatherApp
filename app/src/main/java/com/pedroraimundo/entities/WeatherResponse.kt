package com.pedroraimundo.entities

import com.google.gson.annotations.SerializedName

data class WeatherResponse (@SerializedName("city") var city : City,
                            @SerializedName("main") var forecast : ForecastDetails,
                            @SerializedName("wind") var wind: WindDetails,
)