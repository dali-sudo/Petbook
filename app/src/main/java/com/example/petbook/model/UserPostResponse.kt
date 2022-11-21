package com.example.petbook.model

import com.google.gson.annotations.SerializedName

data class UserPostResponse(


        @SerializedName("_id")
        var id: String,
        @SerializedName("username")
        var username: String,
        @SerializedName("avatar")
        var avatar: String,
        @SerializedName("followerscount")
        var followerscount: String,
        @SerializedName("followingcount")
        var followingcount: String,
        @SerializedName("followers")
        var followers: List<String>,


        )

