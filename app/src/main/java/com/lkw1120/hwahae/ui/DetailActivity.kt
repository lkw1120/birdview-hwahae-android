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
import androidx.lifecycle.ViewModelProviders
import com.lkw1120.hwahae.R
import com.lkw1120.hwahae.databinding.ActivityDetailBinding
import com.lkw1120.hwahae.datasource.entity.Detail
import com.lkw1120.hwahae.viewmodel.DetailViewModel


class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
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
                Toast.makeText(this,"파라미터의 값이 올바르지 않습니다.",Toast.LENGTH_SHORT).show()
                onBackPressed()
            }
            404 -> {
                Toast.makeText(this,"제품이 없습니다.", Toast.LENGTH_SHORT).show()
                onBackPressed()
            }
            500 -> {
                Toast.makeText(this,"서버에 문제가 있습니다.", Toast.LENGTH_SHORT).show()
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
            Toast.makeText(this,"구매 버튼 클릭",Toast.LENGTH_SHORT).show()
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
