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

    fun getProducts(statusCode: MutableLiveData<Int>,
                    products: MutableLiveData<MutableList<Product>>,
                    skin_type: String, page: Int, search: String) {
        when(search) {
            ""   -> {
                ApiConnection.getService()
                    .getProducts(skin_type, page)
                    .enqueue(responseCallback(statusCode,products))
            }
            else -> {
                ApiConnection.getService()
                    .getProducts(skin_type, page, search)
                    .enqueue(responseCallback(statusCode,products))
            }
        }

    }
    fun getDetail(statusCode: MutableLiveData<Int>,
                  detail:MutableLiveData<Detail>,id:Int) {
        ApiConnection.getService()
            .getDetail(id).enqueue(responseCallback(statusCode,detail))
    }


    private fun <T1: Any, T2: ApiResponse> responseCallback(
        statusCode: MutableLiveData<Int>, body: MutableLiveData<T1>): Callback<T2> =
        object:Callback<T2> {

            private var retryCount:Int = 0

            override fun onResponse(call: Call<T2>, response: Response<T2>) {
                if (response.isSuccessful) {
                    retryCount = 0
                    statusCode.postValue(response.body()!!.statusCode)
                    Log.d("Repos","${response.body()!!.statusCode}")
                    if(response.body()!!.statusCode == 200) {
                        body.postValue(response.body()!!.body as T1)
                    }
                    else {
                        Log.d("Repos", response.body()!!.body.toString())
                    }
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

