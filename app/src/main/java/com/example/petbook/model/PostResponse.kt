package com.example.petbook.model

import com.google.gson.annotations.SerializedName
import java.io.FileDescriptor

data class PostResponse(


        @SerializedName("_id")
        var id: String,
        @SerializedName("descreption")
        var description: String,
        @SerializedName("date")
        var date: String,
        @SerializedName("images")
        var images:  List<String>,
        @SerializedName("owner")
        var owner: Owner,
        @SerializedName("likescount")
        var likescount: String,
        @SerializedName("likes")
        var likes: List<String>,
        @SerializedName("tags")
        var tags: List<Tag>

    )

{
data class Owner(
    @SerializedName("_id")
    var id: String,
    @SerializedName("username")
    var username: String,
    @SerializedName("avatar")
    var avatar: String?= null

)
    data class Tag (
        @SerializedName("_id")
        var id: String,
        @SerializedName("name")
        var petname: String,
            )
}
