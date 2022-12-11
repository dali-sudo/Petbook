package com.example.petbook.model

import com.google.gson.annotations.SerializedName

data class GetChatRequest(
    @SerializedName("id1")
    var mid: String,
    @SerializedName("id2")
    var id: String,
)
