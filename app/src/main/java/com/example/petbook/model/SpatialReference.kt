package com.example.petbook.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SpatialReference(

        @SerializedName("wkid")
        @Expose
       val wkid:Int,
        @SerializedName("latestWkid")
        @Expose
        val latestWkid:Int

)
