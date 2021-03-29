package com.pedroraimundo.entities

import com.google.gson.annotations.SerializedName

data class ForecastDetails(@SerializedName("temp") var currentTemp : Double,
                           @SerializedName("temp_min") var minTemp : Double,
                           @SerializedName("temp_max") var maxTemp : Double,
                           @SerializedName("humidity") var humidity : Double,
                           @SerializedName("icon") var icon: String,
)
