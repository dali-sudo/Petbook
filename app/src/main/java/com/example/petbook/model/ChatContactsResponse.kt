package com.example.petbook.model

import com.google.gson.annotations.SerializedName

data class ChatContactsResponse
    (@SerializedName("_id")
     var id: String,
     @SerializedName("Users")
     var Users: List<Data>,

     ) {
    data class Data(
        @SerializedName("_id")
        var id: String,
        @SerializedName("username")
        var username: String,
        @SerializedName("avatar")
        var avatar: String,

        )
}
