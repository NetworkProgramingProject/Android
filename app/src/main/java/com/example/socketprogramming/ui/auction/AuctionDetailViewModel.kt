package com.example.socketprogramming.ui.auction

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.perfumeproject.ui.base.BaseViewModel
import com.example.socketprogramming.data.response.ProductData
import com.example.socketprogramming.databinding.DialogAuctionBinding
import com.example.socketprogramming.network.SocketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class AuctionDetailViewModel @Inject constructor(
    private val socketRepository: SocketRepository
) : BaseViewModel(socketRepository) {


    private var _productList : MutableLiveData<ProductData> = MutableLiveData()
    val productList : LiveData<ProductData> = _productList

    private var _timer : MutableLiveData<String> = MutableLiveData()
    val timer : LiveData<String> = _timer

    private var _auctionBtn : MutableLiveData<Boolean> = MutableLiveData()
    val auctionBtn : LiveData<Boolean> = _auctionBtn

    private var _backBtn : MutableLiveData<Boolean> = MutableLiveData()
    val backBtn : LiveData<Boolean> = _backBtn

    private var _price = MutableLiveData<Int>()
    val price: LiveData<Int> = _price

    private var _auctionMoney = MutableLiveData<Int>()
    val auctionMoney: LiveData<Int> = _auctionMoney


    val priceWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if(!p0.toString().isNullOrEmpty()) {
                _price.value = Integer.parseInt(p0.toString()!!)
            } else {
                _price.value = 0
            }
        }

        override fun afterTextChanged(p0: Editable?) {
        }

    }


    /** 생성자 */
    init {

        _auctionBtn.value = false
        _backBtn.value = false

    }

    fun getProductData(productData: ProductData) {
        _productList.value = productData
        _auctionMoney.value = productData.minPrice
    }

    fun getPrice(price : Int) {
        _auctionMoney.value = price
    }

    fun clickAuction() {
        _auctionBtn.value = true
    }

    fun clickBackBtn() {
        _backBtn.value = true
    }

    /** UI 의 onDestroy 개념으로 생각하면 편할듯 */
    override fun onCleared() {
        super.onCleared()
    }
}
