package com.example.socketprogramming.ui.buy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.perfumeproject.ui.base.BaseViewModel
import com.example.socketprogramming.data.response.ProductData
import com.example.socketprogramming.network.SocketRepository
import com.example.socketprogramming.ui.auction.ProductDataViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BuyViewModel @Inject constructor(
    private val socketRepository: SocketRepository
) : ProductDataViewModel(socketRepository) {

    /** 생성자 */
    init {

    }

    override fun productItemClick(productData: ProductData) {
        TODO("Not yet implemented")
    }

    fun getProductData(result : List<ProductData>) {
        _results.value = result
    }


    /** UI 의 onDestroy 개념으로 생각하면 편할듯 */
    override fun onCleared() {
        super.onCleared()
    }
}