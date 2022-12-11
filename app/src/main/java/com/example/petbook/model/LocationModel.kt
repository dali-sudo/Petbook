package com.example.petbook.model

import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("x")
    val x: Double?,
    @SerializedName("y")
    val y: Double?
)