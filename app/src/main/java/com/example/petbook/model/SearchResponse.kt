package com.example.petbook.model

import com.google.gson.annotations.SerializedName

data class SearchResponse(


    @SerializedName("_id")
    var id: String,
    @SerializedName("username")
    var username: String,
    @SerializedName("avatar")
    var avatar: String,
)
