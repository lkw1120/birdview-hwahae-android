package com.lkw1120.hwahae.ui.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.text.DecimalFormat


object BindingAdapter {
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun setImage(view: ImageView, url: String?) {
        Glide.with(view.context).load(url).into(view)
    }

    @JvmStatic
    @BindingAdapter("price")
    fun setPrice(view: TextView, price: String?) {
        if(price != null) {
            view.text = "${DecimalFormat("###,###").format(price?.toInt())}원"
        }
    }
}
