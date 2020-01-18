package com.lkw1120.hwahae.datasource.remote

import com.lkw1120.hwahae.datasource.entity.Product

data class ProductResponse(override val statusCode:Int,
                           override val body:MutableList<Product>,
                           val scanned_count:Int = 0): ApiResponse
