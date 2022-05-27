package com.example.pagination.network.service

import com.example.pagination.model.UserData
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("users")
    fun getUsers(@Query("page") page : Int): Call<UserData>

}