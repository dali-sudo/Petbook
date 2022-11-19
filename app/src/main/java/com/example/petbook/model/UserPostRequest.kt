package com.example.petbook.model

import com.google.gson.annotations.SerializedName

data class UserPostRequest(
    @SerializedName("id")
    var id : String?= null,
)
