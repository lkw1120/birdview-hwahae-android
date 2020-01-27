package com.lkw1120.hwahae.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
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
import com.lkw1120.hwahae.datasource.remote.ApiResponse
import com.lkw1120.hwahae.datasource.remote.DetailResponse
import com.lkw1120.hwahae.datasource.remote.ErrorResponse
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

        setup()
        dataObserver()
        loadDetail(intent.getIntExtra("id",0))
        slideInAnimation()

    }

    private fun setup() {
        binding.run {
            detailFab.setOnClickListener {
                onBackPressed()
            }
            detailButton.setOnClickListener {
                Toast.makeText(this@DetailActivity,R.string.detail_button_toast,Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun dataObserver() {
        viewModel.getDetail().observe(this,observer())
    }

    private fun observer() = Observer<ApiResponse> {
        when(it.statusCode) {
            200 -> {
                binding.detail = (it as DetailResponse).body
            }
            400 -> {
                Log.d("Error",(it as ErrorResponse).body)
                Toast.makeText(this,R.string.status_code_400,Toast.LENGTH_SHORT).show()
                onBackPressed()
            }
            404 -> {
                Log.d("Error",(it as ErrorResponse).body)
                Toast.makeText(this,R.string.status_code_404, Toast.LENGTH_SHORT).show()
                onBackPressed()
            }
            500 -> {
                Log.d("Error",(it as ErrorResponse).body)
                Toast.makeText(this,R.string.status_code_500, Toast.LENGTH_SHORT).show()
                onBackPressed()
            }

        }
    }

    private fun loadDetail(id: Int) {
        viewModel.loadDetail(id)
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
