package com.lkw1120.hwahae.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lkw1120.hwahae.datasource.entity.Product
import com.lkw1120.hwahae.repository.ApiRepository

class IndexViewModel : ViewModel() {

    private val products: MutableLiveData<MutableList<Product>> = MutableLiveData(ArrayList())
    private var skin_type:String = "oily"
    private var page:Int = 1
    private var search:String = ""

    fun loadProducts(search:String = this.search, skin_type:String = this.skin_type) {
        this.skin_type =
            if(this.skin_type == skin_type) this.skin_type else skin_type
        this.search =
            if(this.search != "") this.search else search
        ApiRepository.getProducts(this.products, this.skin_type, this.page, this.search)
    }

    fun getProducts(): LiveData<MutableList<Product>> = products

    fun nextPage() {
        page++
    }
    fun pageReset() {
        page = 1
    }

}

