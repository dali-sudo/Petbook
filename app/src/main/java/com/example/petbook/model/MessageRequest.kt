package com.example.petbook.model

import com.google.gson.annotations.SerializedName

data class MessageRequest(

    @SerializedName("id")
    var id : String?= null,
    @SerializedName("message")
    var message: String?= null,
    @SerializedName("type")
    var type: String?= null,
    @SerializedName("senderid")
    var senderid : String?= null,

)
