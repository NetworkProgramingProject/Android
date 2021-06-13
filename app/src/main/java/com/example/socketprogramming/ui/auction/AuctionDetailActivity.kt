package com.example.socketprogramming.ui.auction

import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.viewModels
import com.example.socketprogramming.BR
import com.example.socketprogramming.R
import com.example.socketprogramming.data.event.BidPriceUpdate
import com.example.socketprogramming.data.request.AuctionPriceRequest
import com.example.socketprogramming.data.response.ProductData
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
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.math.floor


@AndroidEntryPoint
class AuctionDetailActivity : BaseActivity<ActivityAuctionDetailBinding>(R.layout.activity_auction_detail) {
    override val viewModel: AuctionDetailViewModel by viewModels<AuctionDetailViewModel>()
    @Inject
    lateinit var socketRepository : SocketRepository

    private var countTimer: CountDownTimer? = null

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
            if (it) {
                finish()
            }
        })

        if(!productData!!.sold) {
            init(goodsId)
            settingTimer()

        }

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
                                    if (!productData!!.sold) {
                                        mSocket.emit(
                                            "bid",
                                            gson.toJson(BidPriceUpdate(goodsId, bidPrice))
                                        )
                                    }
                                    Timber.d(
                                        "가격 Post 성공+`${
                                            gson.toJson(
                                                BidPriceUpdate(
                                                    goodsId,
                                                    bidPrice
                                                )
                                            )
                                        }"
                                    )

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

    private fun settingTimer() {
        countTimer = object : CountDownTimer(200000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.tvTimer.text = getTime()
            }

            override fun onFinish() {

            }

        }

        countTimer!!.start()

    }

    private fun getTime(): String? {
        val date = Date()
        Timber.e("date${date}")
        val calendar: Calendar = GregorianCalendar()
        calendar.time = date
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]
        val c_hour = calendar[Calendar.HOUR_OF_DAY]
        val c_min = calendar[Calendar.MINUTE]
        val c_sec = calendar[Calendar.SECOND]


        Timber.e("product ${productData!!.createAt}")
        val createCalendar : Calendar = GregorianCalendar()
        createCalendar.time = formatDateStr(productData!!.createAt)
        val cYear = createCalendar.get(Calendar.YEAR)
        val cMonth = createCalendar.get(Calendar.MONTH)
        val cDay = createCalendar.get(Calendar.DAY_OF_MONTH)
        val cHour = createCalendar.get(Calendar.HOUR_OF_DAY)
        val cMin =  createCalendar.get(Calendar.MINUTE)
        val cSec = createCalendar.get(Calendar.SECOND)


        val baseCal: Calendar = GregorianCalendar(year, month, day, c_hour, c_min, c_sec)
        val targetCal: Calendar = GregorianCalendar(cYear, cMonth, cDay + 1, cHour, cMin, cSec) //비교대상날짜
        val diffSec = (targetCal.timeInMillis - baseCal.timeInMillis) / 1000
        val diffDays = diffSec / (24 * 60 * 60)
        targetCal.add(Calendar.DAY_OF_MONTH, (-diffDays).toInt())
        val hourTime = floor((diffSec / 3600).toDouble()).toInt()
        val minTime = floor(((diffSec - 3600 * hourTime) / 60).toDouble()).toInt()
        val secTime =
            floor((diffSec - 3600 * hourTime - 60 * minTime).toDouble()).toInt()
        val hour = String.format("%02d", hourTime)
        val min = String.format("%02d", minTime)
        val sec = String.format("%02d", secTime)
        return hour + " 시간 " + min + " 분 " + sec + "초 남았습니다."
    }

    @Throws(ParseException::class)
    fun formatDateStr(strDate: String): Date {
        val inputFormatter: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        inputFormatter.setTimeZone(TimeZone.getTimeZone("UTC"))
        return inputFormatter.parse(strDate!!)
    }


    private fun init(goodsId: Int) {

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
        mSocket.emit("enter", data)

    }

    override fun onDestroy() {
        super.onDestroy()
        if(!productData!!.sold) {
            mSocket.disconnect()
        }
    }

    private companion object {
        val FORMAT = SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.KOREA)
        const val EXPECT_TO_FULL = 103
        const val FULL_TO_UP_TIMER = 104
        const val FULL_TO_END = 105
    }
}


