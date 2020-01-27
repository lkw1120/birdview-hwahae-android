package com.lkw1120.hwahae.datasource.entity

data class Product(val id: Int,
                   val thumbnail_image: String,
                   val title: String,
                   val price: String,
                   val oily_score: Int = 0,
                   val dry_score: Int = 0,
                   val sensitive_score: Int = 0)