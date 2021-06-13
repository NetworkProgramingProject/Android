package com.example.socketprogramming.ui.auction

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.socketprogramming.BR
import com.example.socketprogramming.R
import com.example.socketprogramming.data.event.BidPriceUpdate
import com.example.socketprogramming.data.request.AuctionPriceRequest
import com.example.socketprogramming.data.request.SocketRequest
import com.example.socketprogramming.data.response.ProductData
import com.example.socketprogramming.data.response.SocketAuctionResponse
import com.example.socketprogramming.databinding.ActivityAuctionDetailBinding
import com.example.socketprogramming.network.SocketRepository
import com.example.socketprogramming.ui.base.BaseActivity
import com.example.socketprogramming.util.AuctionDialog
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import timber.log.Timber
import java.net.URISyntaxException
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class AuctionDetailActivity : BaseActivity<ActivityAuctionDetailBinding>(R.layout.activity_auction_detail) {
    override val viewModel: AuctionDetailViewModel by viewModels<AuctionDetailViewModel>()
    @Inject
    lateinit var socketRepository : SocketRepository

    lateinit var mSocket : Socket

    private val gson = Gson()


    private val productData: ProductData?
        get() = intent.getSerializableExtra("productData") as ProductData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.vm, viewModel)
        productData?.let { viewModel.getProductData(it) } ?: finish()
        val goodsId = productData!!.id

        viewModel.backBtn.observe(this, androidx.lifecycle.Observer {
          if(it) {
              finish()
          }
        })

        init(goodsId)

        viewModel.auctionBtn.observe(this, androidx.lifecycle.Observer {
            if (it) {
                AuctionDialog(
                    viewModel = viewModel,
                    leftClickListener = {
                        it.dismiss()
                    },
                    rightClickListener = {
                        socketRepository.postAuctionPrice(goodsId = productData!!.id,
                            AuctionPriceRequest(
                                viewModel.price.value!!
                            ),
                            onSuccess = {
                                if (it.success) {
                                    val goodsId = "${productData!!.id}"
                                    var bidPrice = viewModel.price.value!!
                                    mSocket.emit("bid", gson.toJson(BidPriceUpdate(goodsId, bidPrice)))
                                    Timber.d("가격 Post 성공+`${gson.toJson(BidPriceUpdate(goodsId, bidPrice))}")

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
    private fun init(goodsId : Int) {

        try {
            mSocket = IO.socket("http://3.37.7.7:3000")
            mSocket.connect()
            Timber.e("SOCKET - ${mSocket.id()}")
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }
        mSocket.on(Socket.EVENT_CONNECT, onConnect)

        mSocket.on("update") {
            val msg = it[0].toString().toInt()
            runOnUiThread {
                Timber.e("${msg}!!!!!!!")
                viewModel.getPrice(msg)
            }

        }


    }

    private val onConnect = Emitter.Listener {
        val data = "${productData!!.id}"
        Timber.e("socket - goods = $data")
        mSocket.emit("enter",data)

    }

    override fun onDestroy() {
        super.onDestroy()
        mSocket.disconnect()
    }


}
