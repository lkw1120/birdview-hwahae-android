package com.lkw1120.hwahae.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.lkw1120.hwahae.R
import com.lkw1120.hwahae.databinding.ActivityIndexBinding
import com.lkw1120.hwahae.datasource.entity.Product
import com.lkw1120.hwahae.ui.adapter.RecyclerViewAdapter
import com.lkw1120.hwahae.ui.adapter.RecyclerViewDecoration
import com.lkw1120.hwahae.viewmodel.IndexViewModel

class IndexActivity : AppCompatActivity() {

    private lateinit var viewModel: IndexViewModel
    private lateinit var binding: ActivityIndexBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(IndexViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_index)
        binding.lifecycleOwner = this

        recyclerViewSetup()
        recyclerViewObserver()
        recyclerViewLoadProducts()

        searchObserver()
        spinnerObserver()

    }

    private fun recyclerViewSetup() {
        binding.recyclerView.run {
            addItemDecoration(RecyclerViewDecoration(2,
                resources.getDimensionPixelSize(R.dimen.product_item_margin_width),
                resources.getDimensionPixelSize(R.dimen.product_item_margin_height),
                resources.getDimensionPixelSize(R.dimen.product_item_margin_top)))
            layoutManager = GridLayoutManager(this@IndexActivity,2)
            adapter = RecyclerViewAdapter(this@IndexActivity)
        }
    }

    private fun recyclerViewLoadProducts() {
        viewModel.loadProducts()
        binding.scrollView.setOnScrollChangeListener(onScrollChangeListener())
    }

    private fun recyclerViewObserver() {
        viewModel.getProducts().observe(this,productsObserver())
    }



    private fun searchObserver() {
        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                (binding.recyclerView.adapter as RecyclerViewAdapter).clearItems()
                viewModel.pageReset()
                viewModel.loadProducts(query!!,viewModel.getSkinType())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })

    }

    private fun spinnerObserver() {
        binding.spinnerType.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(binding.spinnerType.getItemAtPosition(position)) {
                    "지성"  -> {
                        (binding.recyclerView.adapter as RecyclerViewAdapter).clearItems()
                        viewModel.pageReset()
                        viewModel.loadProducts(viewModel.getSearch(),"oily")
                    }
                    "건성"  -> {
                        (binding.recyclerView.adapter as RecyclerViewAdapter).clearItems()
                        viewModel.pageReset()
                        viewModel.loadProducts(viewModel.getSearch(),"dry")
                    }
                    "민감성" -> {
                        (binding.recyclerView.adapter as RecyclerViewAdapter).clearItems()
                        viewModel.pageReset()
                        viewModel.loadProducts(viewModel.getSearch(),"sensitive")
                    }
                }
            }
        }

    }



    private fun productsObserver() = Observer<MutableList<Product>>{
        (binding.recyclerView.adapter as RecyclerViewAdapter).addItems(it)
    }

    private fun onScrollChangeListener() = NestedScrollView.OnScrollChangeListener {
            v, scrollX, scrollY, oldScrollX, oldScrollY ->
        if(!binding.scrollView.canScrollVertically(1)) {
            viewModel.run{
                nextPage()
                loadProducts()
            }
        }
    }

}
