package com.example.app5.api

import com.example.app5.model.Item
import com.example.app5.model.ItemResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface Service {

    @GET("/search/users?q=location:Fullerton")
    fun getItems(): Call<ItemResponse>

    @GET("/users/{login}/followers")
    fun getFollowers(@Path("login") login: String): Call<List<Item>>

}