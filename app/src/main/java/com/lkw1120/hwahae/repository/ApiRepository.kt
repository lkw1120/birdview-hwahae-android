package com.lkw1120.hwahae.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.lkw1120.hwahae.datasource.remote.ApiResponse
import com.lkw1120.hwahae.datasource.remote.ErrorResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ApiRepository {

    fun getProducts(products: MutableLiveData<ApiResponse>,
                    skin_type: String, page: Int, search: String) {
        when(search) {
            ""   -> {
                ApiConnection.getService()
                    .getProducts(skin_type, page)
                    .enqueue(responseCallback(products))
            }
            else -> {
                ApiConnection.getService()
                    .getProducts(skin_type, page, search)
                    .enqueue(responseCallback(products))
            }
        }
    }

    fun getDetail(detail: MutableLiveData<ApiResponse>, id: Int) {
        ApiConnection.getService()
            .getDetail(id).enqueue(responseCallback(detail))
    }


    private fun responseCallback(data: MutableLiveData<ApiResponse>): Callback<ApiResponse> =
        object:Callback<ApiResponse> {
            private var retryCount:Int = 0
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    retryCount = 0
                    Log.d("Repos","${response.body()!!.statusCode}")
                    data.postValue(response.body()!!)
                }
            }
            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                when (retryCount) {
                    in 0 until 3 -> {
                        retryCount++
                        Log.d("Repos",t.message!!)
                        call.clone().enqueue(this)
                    }
                    else         -> {
                        retryCount = 0
                        data.postValue(ErrorResponse(500,t.message!!))
                    }
                }
            }
        }
}

