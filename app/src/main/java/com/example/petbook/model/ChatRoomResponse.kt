package com.example.petbook.model

import com.google.gson.annotations.SerializedName

data class ChatRoomResponse(
    @SerializedName("_id")
    var id: String,
    @SerializedName("chat")
    var chat: List<Data>,
    @SerializedName("Users")
    var Users: List<Data2>,

) {
    data class Data(
        @SerializedName("message")
        var message: String,
        @SerializedName("senderid")
        var sender_id: String,
    )
    data class Data2(
        @SerializedName("_id")
        var id: String,
        @SerializedName("username")
        var username: String,
    )
}

