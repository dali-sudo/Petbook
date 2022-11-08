package com.example.petbook.model

import androidx.annotation.DrawableRes

data class Post (


    @DrawableRes
    val PostImage : Int,
    val PostUsername : String,
    val PostDate : String,
    val PostDesc : String,
)