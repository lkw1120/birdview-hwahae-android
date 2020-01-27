package com.lkw1120.hwahae.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lkw1120.hwahae.R
import com.lkw1120.hwahae.databinding.ActivityDetailBinding
import com.lkw1120.hwahae.datasource.entity.Detail
import com.lkw1120.hwahae.viewmodel.DetailViewModel


class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider.AndroidViewModelFactory(application)
            .create(DetailViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.lifecycleOwner = this

        dataObserver()
        loadDetail(intent.getIntExtra("id",0))
        slideInAnimation()

        fabListener()
        buttonListener()
    }

    private fun dataObserver() {
        viewModel.getStatusCode().observe(this, statusCodeObserver())
        viewModel.getDetail().observe(this, detailObserver())
    }

    private fun statusCodeObserver() = Observer<Int> { code ->
        when(code) {
            200 -> {

            }
            400 -> {
                Toast.makeText(this,R.string.status_code_400,Toast.LENGTH_SHORT).show()
                onBackPressed()
            }
            404 -> {
                Toast.makeText(this,R.string.status_code_404, Toast.LENGTH_SHORT).show()
                onBackPressed()
            }
            500 -> {
                Toast.makeText(this,R.string.status_code_500, Toast.LENGTH_SHORT).show()
                onBackPressed()
            }
        }
    }

    private fun detailObserver() = Observer<Detail> {
        if(it != null) {
            binding.detail = it
        }
    }

    private fun loadDetail(id: Int) {
        viewModel.loadDetail(id)
    }

    private fun buttonListener() {
        binding.detailButton.setOnClickListener {
            Toast.makeText(this,R.string.detail_button_toast,Toast.LENGTH_SHORT).show()
        }
    }
    private fun fabListener() {
        binding.detailFab.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        slideOutAnimation()
    }

    private fun slideInAnimation() {
        binding.detailActivityLayout.startAnimation(
            AnimationUtils.loadAnimation(this,R.anim.slide_in_bottom))
    }

    private fun slideOutAnimation() {
        binding.detailActivityLayout.startAnimation(
            AnimationUtils.loadAnimation(this,R.anim.slide_out_bottom).apply{
                setAnimationListener(AnimationListener(this@DetailActivity))
            })
    }

    class AnimationListener(private val context: Context): Animation.AnimationListener {
        override fun onAnimationEnd(animation: Animation?) {
            (context as Activity).finish()
        }
        override fun onAnimationRepeat(animation: Animation?) {
        }

        override fun onAnimationStart(animation: Animation?) {
        }
    }
}
