package com.example.petbook.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Extent(

    @SerializedName("xmin")
    @Expose
   val xmin: Double ,
@SerializedName("ymin")
@Expose
val ymin: Double,
@SerializedName("xmax")
@Expose
    val xmax: Double,
@SerializedName("ymax")
@Expose
    val ymax: Double
)
