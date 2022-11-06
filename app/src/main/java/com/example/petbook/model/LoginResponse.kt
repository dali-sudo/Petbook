package com.example.petbook.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @SerializedName("data")
    var user: Data,
    @SerializedName("error")
    var error: String,
    @SerializedName("message")
    var message: String
)
  {
    data class Data(
        @SerializedName("id")
        var id: String,
        @SerializedName("email")
        var email: String,
        @SerializedName("username")
        var username: String,
        @SerializedName("Token")
        var password: String
    )
}
