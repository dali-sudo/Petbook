package com.example.petbook.model

import com.google.gson.annotations.SerializedName

data class getPaginationRequest(
    @SerializedName("id")
    var id: String,
    @SerializedName("limit")
    var limit: String,
    @SerializedName("skip")
    var skip: String,
)
