package com.example.petbook.model

import com.google.gson.annotations.SerializedName

data class LikeRequest(

    @SerializedName("id")
var id: String,
@SerializedName("likescount")
var likescount: String ,
@SerializedName("like")
var likes:String

)
