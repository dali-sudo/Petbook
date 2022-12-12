package com.example.petbook.model

import com.google.gson.annotations.SerializedName

data class NotificationResponse(
    @SerializedName("_id")
    var id : String?= null,
    @SerializedName("sender")
var sender : Sender?= null,
    @SerializedName(" receiver")
    var  receiver: String?= null,
@SerializedName("type")
var type : String?= null,
@SerializedName("title")
var title: String?= null,
@SerializedName("content")
var content: String?= null,
@SerializedName("is_read")
var isRead : String?= null,
@SerializedName("created_at")
var created : String?= null,

)
{
    data class Sender(
        @SerializedName("_id")
        var id: String,
        @SerializedName("username")
        var username: String,
        @SerializedName("avatar")
        var avatar: String?= null

        )
}
