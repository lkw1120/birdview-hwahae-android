package com.lkw1120.hwahae.repository

import com.google.gson.GsonBuilder
import com.lkw1120.hwahae.datasource.remote.ApiResponse
import com.lkw1120.hwahae.repository.gson.GsonDeserializer
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConnection {
    private const val baseUrl: String = "https://6uqljnm1pb.execute-api.ap-northeast-2.amazonaws.com/prod/"
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(jsonConverter())
        .build()

    private fun jsonConverter(): Converter.Factory {
        val builder = GsonBuilder()
        builder.registerTypeAdapter(ApiResponse::class.java,GsonDeserializer())
        return GsonConverterFactory.create(builder.create())
    }

    fun getService(): ApiService =
        retrofit.create(ApiService::class.java)
}