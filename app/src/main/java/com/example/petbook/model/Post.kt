package com.example.petbook.model

import androidx.annotation.DrawableRes

data class Post (


val id:String? = null,
    val PostImage : List<String>? = null,
    val PostUsername : String? = null,
    val PostDate : String? = null,
    val PostDesc : String? = null,
    val likesCount : String? = null,
    val likes:List<String>? = null,
    val PostUserImage:String? = null,
     val tags:Map<String,String>?=null,
val ownerid : String? = null,

)