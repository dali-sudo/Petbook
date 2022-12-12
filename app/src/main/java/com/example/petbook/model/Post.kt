package com.example.petbook.model

import androidx.annotation.DrawableRes

data class Post (


val id:String,
    val PostImage : List<String>,
    val PostUsername : String,
    val PostDate : String,
    val PostDesc : String,
    val likesCount : String,
    val likes:List<String>,
    val PostUserImage:String?= null
)