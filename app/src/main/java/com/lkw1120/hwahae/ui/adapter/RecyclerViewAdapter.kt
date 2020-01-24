package com.lkw1120.hwahae.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lkw1120.hwahae.databinding.ItemProductBinding
import com.lkw1120.hwahae.datasource.entity.Product
import com.lkw1120.hwahae.ui.DetailActivity

class RecyclerViewAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private val items: MutableList<Product> = mutableListOf()
    private var size: Int = 0

    fun addItems(items:List<Product>) {
        this.items.addAll(items)
        notifyItemInserted(size)
        size = this.items.size
    }

    fun clearItems() {
        items.clear()
        notifyDataSetChanged()
        size = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemProductBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(private val binding: ItemProductBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val intent = Intent(it.context, DetailActivity::class.java)
                intent.putExtra("id",binding.item!!.id.toString())
                it.context.startActivity(intent)
            }
        }
        fun bind(item: Product) {
            binding.item = item
        }
    }

}