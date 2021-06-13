package com.example.socketprogramming.ui.sell

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
import com.example.socketprogramming.databinding.SellFragmentBinding
import com.example.socketprogramming.ui.auction.ProductDataAdapter
import com.example.socketprogramming.ui.mypage.MyPageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SellFragment : BaseFragment<SellFragmentBinding>(R.layout.sell_fragment) {

    override val viewModel: SellViewModel by viewModels<SellViewModel>()
    private val productDataAdapter: ProductDataAdapter by lazy { ProductDataAdapter(viewModel) }
    private val myPageViewModel: MyPageViewModel by viewModels<MyPageViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setVariable(BR.vm, viewModel)
        binding.rvAuctionProduct.apply {
            adapter = productDataAdapter
            this.addItemDecoration(VerticalItemDecorator(20))
        }

        if(!myPageViewModel.sellData.value.isNullOrEmpty()) {
            viewModel.getProductData(myPageViewModel.sellData.value!!)
        }
    }

}