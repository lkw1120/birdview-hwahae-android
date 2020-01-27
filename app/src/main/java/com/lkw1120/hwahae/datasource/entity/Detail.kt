package com.lkw1120.hwahae.datasource.entity

data class Detail(val id: Int,
                  val full_size_image: String,
                  val title: String,
                  val description: String,
                  val price: String,
                  val oily_score: Int = 0,
                  val dry_score: Int = 0,
                  val sensitive_score: Int = 0)