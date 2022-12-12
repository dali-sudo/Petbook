package com.example.petbook.model

import com.google.gson.annotations.SerializedName

data class PetResponse(



    @SerializedName("_id")
    var id: String?=null,
    @SerializedName("name")
    var petName : String?= null,
    @SerializedName("type")
    var petType: String?= null,
    @SerializedName("race")
    var race: String?= null,
    @SerializedName("avatar")
    var petPic:String?=null,
    @SerializedName("sexe")
    var sexe:String?=null,
    @SerializedName("owner")
    var petOwner :String?= null,
    @SerializedName("age")
    var petAge:String?=null,
    @SerializedName("images")
    var images: List<String>?=null
)
