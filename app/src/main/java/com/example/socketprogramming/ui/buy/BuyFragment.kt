package com.example.socketprogramming.ui.buy

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.example.perfumeproject.ui.base.BaseFragment
import com.example.perfumeproject.util.VerticalItemDecorator
import com.example.socketprogramming.BR
import com.example.socketprogramming.R
import com.example.socketprogramming.data.response.ProductData
import com.example.socketprogramming.databinding.BuyFragmentBinding
import com.example.socketprogramming.databinding.SellFragmentBinding
import com.example.socketprogramming.ui.auction.AuctionViewModel
import com.example.socketprogramming.ui.auction.ProductDataAdapter
import com.example.socketprogramming.ui.mypage.MyPageViewModel
import com.example.socketprogramming.ui.sell.SellViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BuyFragment : BaseFragment<BuyFragmentBinding>(R.layout.buy_fragment) {

    override val viewModel: BuyViewModel by viewModels<BuyViewModel>()
    private val myPageViewModel: MyPageViewModel by viewModels<MyPageViewModel>()

    private val productDataAdapter: ProductDataAdapter by lazy { ProductDataAdapter(viewModel) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setVariable(BR.vm, viewModel)

        binding.rvAuctionProduct.apply {
            adapter = productDataAdapter
            this.addItemDecoration(VerticalItemDecorator(20))
        }

        if(!myPageViewModel.buyData.value.isNullOrEmpty()) {

            viewModel.getProductData(myPageViewModel.buyData.value!!)
        }
    }

}