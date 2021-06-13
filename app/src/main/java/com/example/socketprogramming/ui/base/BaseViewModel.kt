package com.example.perfumeproject.ui.base

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socketprogramming.network.SocketRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * BaseViewModel
 */
open class BaseViewModel @Inject constructor(
    private val socketRepository: SocketRepository
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    internal val _loginIntent = MutableLiveData<Intent>()
    val loginIntent: LiveData<Intent> = _loginIntent

    /** viewModelScope 에서 Exception 이 발생할 시 처리하는 핸들러 */
    val vmExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Timber.i("$coroutineContext $throwable")
    }

    /** 토스트 메시지 */
    internal val _toastMeesageText = MutableLiveData("")
    val toastMessageText: LiveData<String> = _toastMeesageText

    /** 생성자 개념으로 생각하면 편할듯 */
    init {
        /**
         * viewModelScope 를 사용하면 lifecycle 을 인식하는 CoroutineScope 를 만들 수 있다.
         * viewModelScope 블록에서 실행되는 작업은 별도의 처리를 하지 않아도
         * ViewModel 이 clear 되는 순간 자동으로 취소된다.
         *
         * @see [[Android & Coroutine] ViewModelScope, LiveData Builder 사용하기](https://zion830.tistory.com/64)
         */
        viewModelScope.launch {}
    }

    /** UI 의 onDestroy 개념으로 생각하면 편할듯 */
    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }


    /**
     * [Disposable] 객체를 [compositeDisposable] 에 넣는다.
     * [compositeDisposable] 에 추가된 [Disposable] 들은
     * [onCleared] 단계에서 모두 clear 된다.
     */
    protected fun Disposable.addDisposable() {
        compositeDisposable.add(this)
    }

    /**
     * UI 단계에서 onResume 일 시 실행해주어야 하는 로직을 명세한다.
     * 필수가 아니므로 추상화는 하지 않는다.
     */
    open fun onResume() {
    }

    open fun onBackPressed() {

    }
}
