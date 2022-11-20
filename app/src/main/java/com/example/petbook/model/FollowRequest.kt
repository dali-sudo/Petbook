package com.example.petbook.model

import com.google.gson.annotations.SerializedName

data class FollowRequest(

    @SerializedName("myid")
    var id : String?= null,
    @SerializedName("followed")
    var followed : String?= null,
)

