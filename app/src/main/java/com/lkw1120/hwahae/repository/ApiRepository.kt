package com.lkw1120.hwahae.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.lkw1120.hwahae.datasource.entity.Detail
import com.lkw1120.hwahae.datasource.entity.Product
import com.lkw1120.hwahae.datasource.remote.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ApiRepository {

    fun getProducts(products: MutableLiveData<MutableList<Product>>,
                    skin_type: String = "oily", page: Int = 1, search: String = "") {
        when(search) {
            ""   -> {
                ApiConnection.getService().getProducts(skin_type, page)
                    .enqueue(responseCallback(products))
            }
            else -> {
                ApiConnection.getService().getProducts(skin_type, page, search)
                    .enqueue(responseCallback(products))
            }
        }

    }

    fun getDetail(detail:MutableLiveData<Detail>,id:Int = 0) {

        ApiConnection.getService().getDetail(id)
            .enqueue(responseCallback(detail))
    }

    private fun <T1: Any, T2: ApiResponse> responseCallback(data: MutableLiveData<T1>): Callback<T2> =
        object:Callback<T2> {

            private var retryCount:Int = 0

            override fun onResponse(call: Call<T2>, response: Response<T2>) {
                if (response.isSuccessful) {
                    retryCount = 0
                    Log.d("Repos","${response.body()!!.statusCode}")
                    data.postValue(response.body()!!.body as T1)
                }
            }

            override fun onFailure(call: Call<T2>, t: Throwable) {
                Log.d("Repos","${t.message}")
                when (retryCount) {
                    in 0 until 5 -> {
                        retryCount++
                        Log.d("Repos","Retry " + retryCount)
                        call.clone().enqueue(this)
                    }
                    else         -> {
                        retryCount = 0
                        onFailure(call,Throwable())

                    }
                }
            }
        }


}

