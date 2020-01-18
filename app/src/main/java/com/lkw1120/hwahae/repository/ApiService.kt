package com.lkw1120.hwahae.repository

import com.lkw1120.hwahae.datasource.remote.DetailResponse
import com.lkw1120.hwahae.datasource.remote.ProductResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("products?")
    fun getProducts(
        @Query("skin_type") skin_type: String,
        @Query("page") page: Int,
        @Query("search") search: String): Call<ProductResponse>


    @GET("products?")
    fun getProducts(
        @Query("skin_type") skin_type: String,
        @Query("page") page: Int): Call<ProductResponse>


    @GET("products/{id}")
    fun getDetail(
        @Path("id") id: Int): Call<DetailResponse>

}