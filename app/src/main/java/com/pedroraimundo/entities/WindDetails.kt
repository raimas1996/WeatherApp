package com.pedroraimundo.entities

import com.google.gson.annotations.SerializedName

data class WindDetails(@SerializedName("speed") var windSpeed : Double,
)