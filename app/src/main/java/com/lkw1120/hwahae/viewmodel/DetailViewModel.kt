package com.lkw1120.hwahae.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lkw1120.hwahae.datasource.entity.Detail
import com.lkw1120.hwahae.repository.ApiRepository

class DetailViewModel: ViewModel() {
    private val detail: MutableLiveData<Detail> = MutableLiveData()

    fun loadDetail(id: Int) {
        ApiRepository.getDetail(detail,id)
    }

    fun getDetail(): LiveData<Detail> = detail
}