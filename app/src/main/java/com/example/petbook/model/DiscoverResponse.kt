package com.example.petbook.model

import com.google.gson.annotations.SerializedName

data class DiscoverResponse(
    @SerializedName("_id")
    var id: String,
    @SerializedName("image")
    var Image: String
)
