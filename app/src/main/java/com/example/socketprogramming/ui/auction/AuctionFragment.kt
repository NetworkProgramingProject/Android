package com.example.socketprogramming.ui.auction

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.perfumeproject.ui.base.BaseFragment
import com.example.perfumeproject.util.VerticalItemDecorator
import com.example.socketprogramming.BR
import com.example.socketprogramming.R
import com.example.socketprogramming.databinding.AuctionFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuctionFragment : BaseFragment<AuctionFragmentBinding>(R.layout.auction_fragment) {

    override val viewModel: AuctionViewModel by viewModels<AuctionViewModel>()
    private val productDataAdapter: ProductDataAdapter by lazy { ProductDataAdapter(viewModel) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setVariable(BR.vm, viewModel)

        binding.rvAuctionProduct.apply {
            adapter = productDataAdapter
            this.addItemDecoration(VerticalItemDecorator(20))
        }

        binding.apply {
            // 텍스트 값 세팅
        }
    }

}