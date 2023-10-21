package com.example.cricbuzztask.Model

data class SneakerDetails(val sneakerDetails:ArrayList<Sneaker>)

data class Sneaker(val id:String,val brand:String,val colorway:String,
                   val gender:String,val releaseDate:String,val retailPrice:String,val styleId:String,
    val shoe:String,val name:String,val title:String,val year:String,val media:Media)

data class Media(val imageUrl:String)
