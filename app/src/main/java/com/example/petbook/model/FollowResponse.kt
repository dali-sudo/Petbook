package com.example.petbook.model

import com.google.gson.annotations.SerializedName

data class FollowResponse(
    @SerializedName("data")
    var response: Data,
    @SerializedName("error")
    var error: String,
    @SerializedName("message")
    var message: String
)
{
    data class Data(
        @SerializedName("myid")
        var id: String,
        @SerializedName("followed")
        var followed: String,

    )
}

