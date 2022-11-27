package com.example.petbook.model

import com.google.gson.annotations.SerializedName

data class GetChatResponse(
    @SerializedName("id")
    var id: String,
)
