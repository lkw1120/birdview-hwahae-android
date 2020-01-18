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
import androidx.lifecycle.ViewModelProviders
import com.lkw1120.hwahae.R
import com.lkw1120.hwahae.databinding.ActivityDetailBinding
import com.lkw1120.hwahae.viewmodel.DetailViewModel


class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.lifecycleOwner = this

        viewModel.getDetail().observe(this, Observer {
            binding.detail = it
            Log.d("Test","여기까진 오나????")
        })

        viewModel.loadDetail(intent.getStringExtra("id")!!.toInt())

        slideInAnimation()

        fabListener()
        buttonListener()

    }

    private fun buttonListener() {
        binding.detailButton.setOnClickListener {
            Toast.makeText(this,"구매 버튼 클릭!",Toast.LENGTH_SHORT).show()
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
