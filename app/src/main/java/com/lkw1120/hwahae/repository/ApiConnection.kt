package com.lkw1120.hwahae.repository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConnection {
    private const val baseUrl: String = "https://6uqljnm1pb.execute-api.ap-northeast-2.amazonaws.com/prod/"
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getService(): ApiService = retrofit.create(
        ApiService::class.java)
}