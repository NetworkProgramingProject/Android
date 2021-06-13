package com.example.socketprogramming.ui.mypage

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.perfumeproject.ui.base.BaseFragment
import com.example.socketprogramming.BR
import com.example.socketprogramming.R
import com.example.socketprogramming.databinding.AuctionFragmentBinding
import com.example.socketprogramming.databinding.MyPageFragmentBinding
import com.example.socketprogramming.ui.auction.AuctionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment : BaseFragment<MyPageFragmentBinding>(R.layout.my_page_fragment) {

    override val viewModel: MyPageViewModel by viewModels<MyPageViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setVariable(BR.vm, viewModel)

        binding.apply {
            fragmentManager = childFragmentManager
            vm = viewModel
            lifecycleOwner = this@MyPageFragment.viewLifecycleOwner
        }

        binding.apply {
            // 텍스트 값 세팅
        }
    }

}