package com.example.petbook.model

import com.google.gson.annotations.SerializedName

data class PetRequest(
    @SerializedName("_id")
    var petId : String?= null,
    @SerializedName("name")
    var petName : String?= null,
    @SerializedName("type")
    var petType: String?= null,
    @SerializedName("race")
    var race: String?= null,
    @SerializedName("owner")
    var petOwner :String?= null,
    @SerializedName("avatar")
    var petPic:String?=null,
    @SerializedName("sexe")
    var sexe:String?=null,
    @SerializedName("age")
    var petAge:String?=null

)
