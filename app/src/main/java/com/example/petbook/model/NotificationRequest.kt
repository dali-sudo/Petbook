package com.example.petbook.model

import com.google.gson.annotations.SerializedName

data class NotificationRequest(
    @SerializedName("id")
    var id : String?= null,
    @SerializedName("num")
    var num : String?= null,
)
