package com.example.petbook.model

import com.google.gson.annotations.SerializedName

data class LoginRequest(

    @SerializedName("username")
    var username : String?= null,
    @SerializedName("email")
    var email: String,
    @SerializedName("password")
    var password: String,
    @SerializedName("token")
    var token :String?= null,
    @SerializedName("profilePic")
    var profilePic:String?=null
)
