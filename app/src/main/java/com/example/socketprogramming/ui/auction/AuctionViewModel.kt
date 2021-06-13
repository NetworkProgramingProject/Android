package com.example.socketprogramming.ui.auction

import android.content.Intent
import androidx.lifecycle.ViewModel
import com.example.perfumeproject.ui.base.BaseViewModel
import com.example.socketprogramming.SocketApplication
import com.example.socketprogramming.data.response.ProductData
import com.example.socketprogramming.network.SocketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import java.net.SocketPermission
import javax.inject.Inject


@HiltViewModel
class AuctionViewModel @Inject constructor(
    private val socketRepository: SocketRepository
) : ProductDataViewModel(socketRepository) {

    /** 생성자 */
    init {
        _productSize.value = 0
        getProductData()
    }

    private fun getProductData() {
        socketRepository.getProductList(
            onSuccess = {
                if(!it.isNullOrEmpty()) {
                    _results.value = it!!
                    _productSize.value = it!!.size
                    Timber.d("경매 상품 불러오기 성공")
                }

            }, onFailure = {
            Timber.d("경매 상품 불러오기 실패")
        })
    }

    override fun productItemClick(productData: ProductData) {
        Intent(SocketApplication.appContext, AuctionDetailActivity::class.java).apply {
            putExtra("productData", productData)
        }.run {
            SocketApplication.getGlobalApplicationContext()
                .startActivity(this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }
    }

    /** UI 의 onDestroy 개념으로 생각하면 편할듯 */
    override fun onCleared() {
        super.onCleared()
    }
}
