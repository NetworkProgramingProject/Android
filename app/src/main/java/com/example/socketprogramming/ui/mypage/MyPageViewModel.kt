package com.example.socketprogramming.ui.mypage

import androidx.lifecycle.ViewModel
import com.example.perfumeproject.ui.base.BaseViewModel
import com.example.socketprogramming.network.SocketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val socketRepository: SocketRepository
) : BaseViewModel(socketRepository) {

    /** 생성자 */
    init {

    }

    /** UI 의 onDestroy 개념으로 생각하면 편할듯 */
    override fun onCleared() {
        super.onCleared()
    }
}
