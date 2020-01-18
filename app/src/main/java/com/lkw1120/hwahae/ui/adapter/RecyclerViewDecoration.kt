package com.lkw1120.hwahae.ui.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewDecoration(
    private val spanCount: Int,
    private val width: Int,
    private val height: Int,
    private val top: Int
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {

        val position: Int = parent.getChildAdapterPosition(view)
        val column: Int = position % spanCount
        outRect.left = column * width / spanCount
        outRect.right =
            width - (column + 1) * width / spanCount
        outRect.top =
            when (position) {
                in 0 until spanCount
                     -> top
                else
                     -> height
            }
    }

}