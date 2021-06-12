package com.example.socketprogramming.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.perfumeproject.ui.base.navigate
import com.example.socketprogramming.BR
import com.example.socketprogramming.R
import com.example.socketprogramming.databinding.ActivityHomeBinding
import com.example.socketprogramming.ui.auction.AuctionFragment
import com.example.socketprogramming.ui.base.BaseActivity
import com.example.socketprogramming.ui.mypage.MyPageFragment
import com.example.socketprogramming.ui.product.ProductFragment
import com.example.socketprogramming.util.ConfirmDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>(R.layout.activity_home) {
    override val viewModel: HomeViewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.vm, viewModel)

        viewModel.selectHome.observe(this, Observer {
            if (it) {
                AuctionFragment().navigate(supportFragmentManager, binding.flMain.id)
            }
        })

        viewModel.selectMyPage.observe(this, Observer {
            if (it) {
                MyPageFragment().navigate(supportFragmentManager, binding.flMain.id)
            }
        })

        viewModel.selectProduct.observe(this, Observer {
            if (it) {
                ProductFragment().navigate(supportFragmentManager, binding.flMain.id)
            }
        })


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fragment = supportFragmentManager.findFragmentById(binding.flMain.id)
        fragment!!.onActivityResult(requestCode, resultCode, data)

    }
}