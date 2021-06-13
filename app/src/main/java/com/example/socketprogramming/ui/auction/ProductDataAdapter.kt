package com.example.socketprogramming.ui.auction

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.socketprogramming.R
import com.example.socketprogramming.data.response.ProductData
import com.example.socketprogramming.databinding.ItemProductBinding
import com.example.socketprogramming.di.AuthManager
import com.example.socketprogramming.util.setOnSingleClickListener

/**
 * 경매 상품 recyclerview adapter
 */
class ProductDataAdapter(val vm: ProductDataViewModel) :
    ListAdapter<ProductData, ProductViewHolder>(ProductDataDiffUtilCallBack) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemProductBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_product, parent, false)

        return ProductViewHolder(binding).apply {
            binding.root.setOnSingleClickListener {
                vm.productItemClick(binding.productData!!)
            }
        }
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position), vm)
    }
}

class ProductViewHolder(private val binding: ItemProductBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(productData: ProductData, vm: ProductDataViewModel) {
        binding.productData = productData
    }
}

object ProductDataDiffUtilCallBack : DiffUtil.ItemCallback<ProductData>() {
    override fun areItemsTheSame(
            oldItem: ProductData,
            newItem: ProductData
    ): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(
            oldItem: ProductData,
            newItem: ProductData
    ): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}
