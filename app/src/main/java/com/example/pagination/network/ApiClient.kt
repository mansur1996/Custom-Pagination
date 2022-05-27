package com.example.pagination.network

import com.example.pagination.network.service.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private val IS_TESTER = true
    private val SERVER_DEVELOPMENT = "https://reqres.in/api/"
    private val SERVER_PRODUCTION = "https://jsonplaceholder.typicode.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(server())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private fun server(): String {
        if (IS_TESTER) return SERVER_DEVELOPMENT
        return SERVER_PRODUCTION
    }

    val apiService : ApiService = retrofit.create(ApiService::class.java)
}