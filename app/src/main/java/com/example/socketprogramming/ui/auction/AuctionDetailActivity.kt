package com.example.socketprogramming.ui.auction

import android.os.Bundle
import com.example.socketprogramming.databinding.ActivityAuctionDetailBinding
import androidx.activity.viewModels
import com.example.socketprogramming.BR
import com.example.socketprogramming.R
import com.example.socketprogramming.data.request.AuctionPriceRequest
import com.example.socketprogramming.data.response.ProductData
import com.example.socketprogramming.network.SocketRepository
import com.example.socketprogramming.ui.base.BaseActivity
import com.example.socketprogramming.util.AuctionDialog
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.Observer
import javax.inject.Inject


@AndroidEntryPoint
class AuctionDetailActivity : BaseActivity<ActivityAuctionDetailBinding>(R.layout.activity_auction_detail) {
    override val viewModel: AuctionDetailViewModel by viewModels<AuctionDetailViewModel>()
    @Inject
    lateinit var socketRepository : SocketRepository

    private val productData: ProductData?
        get() = intent.getSerializableExtra("productData") as ProductData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.vm, viewModel)

        socketRepository.socketJoin(productData!!.id, onData = {
            runOnUiThread{
                Timber.e("$it")
            }
        })


        productData?.let { viewModel.getProductData(it) } ?: finish()


        viewModel.auctionBtn.observe(this, androidx.lifecycle.Observer {
            if (it) {
                AuctionDialog(
                    viewModel = viewModel,
                    leftClickListener = {
                        it.dismiss()
                    },
                    rightClickListener = {
                        socketRepository.postAuctionPrice(goodsId = productData!!.id,  AuctionPriceRequest(viewModel.price.value!!),
                            onSuccess = {
                                if(it.success) {
                                    Timber.e("가격 Post 성공")
                                }
                            },
                            onFailure = {

                            }
                        )
                        it.dismiss()

                    },
                    cancelable = false
                ).show(supportFragmentManager, "LoginWarningDialog")
            }
        })
    }


}
