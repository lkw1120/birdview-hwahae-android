package com.lkw1120.hwahae.datasource.remote

import com.lkw1120.hwahae.datasource.entity.Detail

data class DetailResponse(override val statusCode:Int,
                          override val body: Detail): ApiResponse
