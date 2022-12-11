package com.example.petbook.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PlacesApiResponse(
    @SerializedName("spatialReference")
    @Expose
    val spatialReference : SpatialReference ,
        @SerializedName("candidates")
        val candidates :List<Candidate>?

)
