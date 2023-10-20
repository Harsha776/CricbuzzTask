package com.example.cricbuzztask.Network

import retrofit2.http.GET

interface Api {

    @GET("someData")
    suspend fun getAllData(): List<Any>
}