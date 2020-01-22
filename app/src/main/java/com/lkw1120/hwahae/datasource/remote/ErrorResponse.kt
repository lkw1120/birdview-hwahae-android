package com.lkw1120.hwahae.datasource.remote

data class ErrorResponse(override val statusCode:Int,
                          override val body: String): ApiResponse