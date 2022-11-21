package com.example.petbook.views

class gridItemModel {

    lateinit var description:String
    var image:Int? = null


    constructor(description:String,image:Int)
    {
        this.description= description
        this.image=image
    }
}