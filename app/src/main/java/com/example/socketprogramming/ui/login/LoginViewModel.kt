package com.example.socketprogramming.ui.login

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.perfumeproject.ui.base.BaseViewModel
import com.example.socketprogramming.data.request.LoginRequest
import com.example.socketprogramming.di.AuthManager
import com.example.socketprogramming.network.SocketRepository
import com.example.socketprogramming.util.safeEnqueue
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val socketRepository: SocketRepository,
    private val authManager: AuthManager
) : BaseViewModel(socketRepository) {

    private var _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private var _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private var _login = MutableLiveData<Boolean>()
    val login: LiveData<Boolean> = _login

    private var _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> = _loginSuccess

    private var _checking = MutableLiveData<Boolean>()
    val checking: LiveData<Boolean> = _checking

    private var _register = MutableLiveData<Boolean>()
    val register: LiveData<Boolean> = _register

    /** 생성자 */
    init {
        _login.value = false
        _register.value = false
        _checking.value = false
        _loginSuccess.value = false

    }

    val emailCheckWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if (!p0.toString().isNullOrEmpty()) {
               _email.value = p0.toString()
            } else {
                _email.value = ""
            }
            checking()

        }

        override fun afterTextChanged(p0: Editable?) {
        }

    }

    val passwordCheckWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if (!p0.toString().isNullOrEmpty()) {
              _password.value = p0.toString()
            } else {
                _password.value = ""
            }
            checking()
        }

        override fun afterTextChanged(p0: Editable?) {
        }

    }


    fun checkingLogin() {
        if(_checking.value!!) {
            socketRepository.postLogin(LoginRequest(_email.value!! , _password.value!!),
            onSuccess = {
                Timber.e("${it.status}")
                if(it.success) {
                    authManager.token = it.data!!
                    authManager.autoLogin = true
                    Timber.d("로그인 성공 - ${it.data!!}")
                    _loginSuccess.value = true
                }
            },
            onFailure = {

            })
        }
    }

    private fun checking() {
        if(! _password.value.isNullOrEmpty() && !_email.value.isNullOrEmpty()) {
            _checking.value = _password.value!! != "" && _email.value!! != ""
        } else {
            _checking.value = false
        }
    }


    fun clickLoginButton() {
        _login.value = !_login.value!!
        Timber.e("로그인 화면 - ${_login.value!!}")

    }
    fun clickRegisterButton() {
        _register.value = true
    }

    override fun onBackPressed() {
        _login.value = false
        Timber.e("백버튼 !")
    }

}
