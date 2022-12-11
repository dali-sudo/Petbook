package com.example.petbook.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.jar.Attributes

data class Candidate(
    @SerializedName("address")
        @Expose
        val address:String,

    @SerializedName("location")
        @Expose
       val location:Location,
    @SerializedName("score")
        @Expose
        val score: Int,
    @SerializedName("attributes")
        @Expose
        val attributes: Attributes?,
    @SerializedName("extent")
        @Expose
       val extent: Extent

)
