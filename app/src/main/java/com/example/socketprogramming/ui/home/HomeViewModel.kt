package com.example.socketprogramming.ui.home

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.perfumeproject.ui.base.BaseViewModel
import com.example.socketprogramming.di.AuthManager
import com.example.socketprogramming.network.SocketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val socketRepository: SocketRepository,
    private val auth: AuthManager,
) : BaseViewModel(socketRepository) {

    private var _selectHome : MutableLiveData<Boolean> = MutableLiveData()
    val selectHome : LiveData<Boolean> = _selectHome

    private var _selectProduct : MutableLiveData<Boolean> = MutableLiveData()
    val selectProduct : LiveData<Boolean> = _selectProduct

    private var _selectMyPage : MutableLiveData<Boolean> = MutableLiveData()
    val selectMyPage : LiveData<Boolean> = _selectMyPage

    /** 생성자 */
    init {
        _selectHome.value = true
        _selectMyPage.value = false
        _selectProduct.value = false
    }

    fun homeClick() {
        _selectHome.value = true
        _selectMyPage.value = false
        _selectProduct.value = false
    }

    fun mypageClick() {
        _selectHome.value = false
        _selectMyPage.value = true
        _selectProduct.value = false
    }

    fun productClick() {
        _selectHome.value = false
        _selectMyPage.value = false
        _selectProduct.value = true
    }


    /** UI 의 onDestroy 개념으로 생각하면 편할듯 */
    override fun onCleared() {
        super.onCleared()
    }

    override fun onResume() {
        super.onResume()
    }
}

