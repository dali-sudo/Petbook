package com.example.petbook.model

import com.google.gson.annotations.SerializedName

data class DeleteResponse(
    @SerializedName("message")
    var response: String

)
