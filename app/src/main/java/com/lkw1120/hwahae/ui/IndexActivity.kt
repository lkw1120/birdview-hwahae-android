package com.lkw1120.hwahae.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        viewModel = ViewModelProvider.AndroidViewModelFactory(application)
            .create(IndexViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_index)
        binding.lifecycleOwner = this

        setup()
        dataObserver()
        loadProducts()

        searchListener()
        skinTypeListener()
    }

    private fun setup() {
        binding.run {
            recyclerView.run {
                addItemDecoration(
                    RecyclerViewDecoration(
                        2,
                        resources.getDimensionPixelSize(R.dimen.product_item_margin_width),
                        resources.getDimensionPixelSize(R.dimen.product_item_margin_height),
                        resources.getDimensionPixelSize(R.dimen.product_item_margin_top)
                    )
                )
                layoutManager = GridLayoutManager(this@IndexActivity, 2)
                adapter = RecyclerViewAdapter(this@IndexActivity)
            }
            scrollView.setOnScrollChangeListener(onScrollChangeListener())
        }
    }

    private fun dataObserver() {
        viewModel.getStatusCode().observe(this,statusCodeObserver())
        viewModel.getProducts().observe(this,productsObserver())
    }

    private fun statusCodeObserver() = Observer<Int> { code ->
        when(code) {
            200 -> {
                viewModel.nextPage()
            }
            400 -> {
                binding.progressIcon.visibility = View.INVISIBLE
                Toast.makeText(this,R.string.status_code_400,Toast.LENGTH_SHORT).show()
            }
            404 -> {
                binding.progressIcon.visibility = View.INVISIBLE
                Toast.makeText(this,R.string.status_code_404, Toast.LENGTH_SHORT).show()
            }
            500 -> {
                binding.progressIcon.visibility = View.INVISIBLE
                Toast.makeText(this,R.string.status_code_500, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun productsObserver() = Observer<MutableList<Product>>{
        if(it != null) {
            if(it.size < 20) {
                binding.progressIcon.visibility = View.INVISIBLE
            }
            else {
                binding.progressIcon.visibility = View.VISIBLE
            }
            (binding.recyclerView.adapter as RecyclerViewAdapter).addItems(it)
        }
    }

    private fun loadProducts() {
        viewModel.loadProducts()
    }

    private fun onScrollChangeListener() = NestedScrollView.OnScrollChangeListener {
            _, _, _, _, _ ->
        if(!binding.scrollView.canScrollVertically(1) &&
            binding.recyclerView.scrollState == RecyclerView.SCROLL_STATE_SETTLING) {
            viewModel.loadProducts()
        }
    }

    private fun searchListener() {
        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                (binding.recyclerView.adapter as RecyclerViewAdapter).clearItems()
                viewModel.resetPage()
                viewModel.loadProducts(query!!,viewModel.getSkinType())
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean = false
        })
    }

    private fun skinTypeListener() {
        binding.spinnerSkinType.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(binding.spinnerSkinType.getItemAtPosition(position)) {
                    "지성"  -> {
                        (binding.recyclerView.adapter as RecyclerViewAdapter).clearItems()
                        viewModel.resetPage()
                        viewModel.loadProducts(viewModel.getSearch(),"oily")
                    }
                    "건성"  -> {
                        (binding.recyclerView.adapter as RecyclerViewAdapter).clearItems()
                        viewModel.resetPage()
                        viewModel.loadProducts(viewModel.getSearch(),"dry")
                    }
                    "민감성" -> {
                        (binding.recyclerView.adapter as RecyclerViewAdapter).clearItems()
                        viewModel.resetPage()
                        viewModel.loadProducts(viewModel.getSearch(),"sensitive")
                    }
                }
            }
        }
    }
}
