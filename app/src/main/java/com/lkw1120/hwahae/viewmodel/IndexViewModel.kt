package com.lkw1120.hwahae.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lkw1120.hwahae.datasource.entity.Product
import com.lkw1120.hwahae.repository.ApiRepository

class IndexViewModel : ViewModel() {

    private val products: MutableLiveData<MutableList<Product>> = MutableLiveData(ArrayList())
    private var skinType:String = "oily"
    private var page:Int = 1
    private var search:String = ""

    fun loadProducts(search:String = this.search, skinType:String = this.skinType) {
        this.skinType =
            if(this.skinType == skinType) this.skinType else skinType
        this.search =
            if(this.search == search && this.search != "") this.search else search
        ApiRepository.getProducts(this.products, this.skinType, this.page, this.search)
    }

    fun nextPage() {
        page++
    }
    fun pageReset() {
        page = 1
    }

    fun getProducts(): LiveData<MutableList<Product>> = products

    fun getSkinType() = skinType
    fun getSearch() = search
}

