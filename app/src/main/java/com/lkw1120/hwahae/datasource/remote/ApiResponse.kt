package com.lkw1120.hwahae.datasource.remote

interface ApiResponse {
    val statusCode:Int
    val body:Any
}