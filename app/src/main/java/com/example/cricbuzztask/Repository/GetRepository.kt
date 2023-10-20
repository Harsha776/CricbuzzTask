package com.example.cricbuzztask.Repository

import com.example.cricbuzztask.Network.RetrofitBuilder

class GetRepository {
    suspend fun getData(): List<Any> = RetrofitBuilder.api.getAllData()
}