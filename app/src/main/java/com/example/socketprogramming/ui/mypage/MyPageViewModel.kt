package com.example.socketprogramming.ui.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.perfumeproject.ui.base.BaseViewModel
import com.example.socketprogramming.data.response.ProductData
import com.example.socketprogramming.data.response.UserResponse
import com.example.socketprogramming.network.SocketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val socketRepository: SocketRepository
) : BaseViewModel(socketRepository) {

    private val _tabItems: MutableLiveData<List<String>> = MutableLiveData()
    private val _position: MutableLiveData<Int> = MutableLiveData()
    val tabItems: LiveData<List<String>> get() = _tabItems
    val position: LiveData<Int> get() = _position

    private var _nickName = MutableLiveData<String>()
    val nickName: LiveData<String> = _nickName

    private val _sellData: MutableLiveData<List<ProductData>> = MutableLiveData()
    val sellData: LiveData<List<ProductData>> get() = _sellData

    private val _buyData: MutableLiveData<List<ProductData>> = MutableLiveData()
    val buyData: LiveData<List<ProductData>> get() = _buyData

    /** 생성자 */
    init {
        _tabItems.postValue(TAB_ITEMS)
        getUserData()
    }

    fun getUserData() {
        socketRepository.getUserData(onSuccess ={
            _nickName.value = it.nick
            if(!it.buy.isNullOrEmpty()) {
                _buyData.value = it.buy!!
            }
            if(!it.sell.isNullOrEmpty()) {
                _sellData.value = it.sell!!
            }
        }, onFailure = {
            Timber.d("사용자 정보 가져오기 실패")
        })
    }

    fun selectPosition(position: Int) {
        _position.postValue(position)
    }

    companion object {
        private val TAB_ITEMS = listOf("BUY","SELL")
    }

    /** UI 의 onDestroy 개념으로 생각하면 편할듯 */
    override fun onCleared() {
        super.onCleared()
    }
}
