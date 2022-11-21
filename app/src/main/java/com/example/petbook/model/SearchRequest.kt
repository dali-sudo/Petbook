package com.example.petbook.model

import com.google.gson.annotations.SerializedName

data class SearchRequest(

    @SerializedName("username")
    var username : String?= null,
)
