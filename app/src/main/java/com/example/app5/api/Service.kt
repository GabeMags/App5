package com.example.app5.api

import com.example.app5.model.ItemResponse
import retrofit2.Call
import retrofit2.http.GET

interface Service {

    @GET("/search/users?q=language:java+location:lagos")
    fun getItems(): Call<ItemResponse?>?
}