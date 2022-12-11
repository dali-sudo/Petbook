package com.example.petbook.model

import com.google.gson.annotations.SerializedName

data class PostRequest(
    @SerializedName("descreption")
    var descreption: String,
    @SerializedName("images")
    var images:  List<String>,
    @SerializedName("owner")
    var owner:String,
    @SerializedName("tags")
    var tags:  List<String>



)
