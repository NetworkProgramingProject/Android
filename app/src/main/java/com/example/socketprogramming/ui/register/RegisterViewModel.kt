package com.example.socketprogramming.ui.register

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.perfumeproject.ui.base.BaseViewModel
import com.example.socketprogramming.data.request.RegisterRequest
import com.example.socketprogramming.di.AuthManager
import com.example.socketprogramming.network.SocketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import java.util.regex.Pattern
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
     private val socketRepository: SocketRepository,
     private val authManager: AuthManager
) : BaseViewModel(socketRepository) {

    private var _checking = MutableLiveData<Boolean>()
    val checking: LiveData<Boolean> = _checking

    private var _nickName = MutableLiveData<String>()
    val nickName: LiveData<String> = _nickName

    private var _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private var _passwordCheck = MutableLiveData<String>()
    val passwordCheck: LiveData<String> = _passwordCheck

    private var _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private var _emailWarning = MutableLiveData<Boolean>()
    val emailWarning: LiveData<Boolean> = _emailWarning

    private var _pwWarning = MutableLiveData<Boolean>()
    val pwWarning: LiveData<Boolean> = _pwWarning

    private var _registerSuccess = MutableLiveData<Boolean>()
    val registerSuccess: LiveData<Boolean> = _registerSuccess

    /** 생성자 */
    init {
        _emailWarning.value = false
        _pwWarning.value = false
        _checking.value = false
        _registerSuccess.value = false
    }


    val nickNameWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if(!p0.toString().isNullOrEmpty()) {
                _nickName.value = p0.toString()
            } else {
                _nickName.value = ""
            }
            checkRegister()

        }

        override fun afterTextChanged(p0: Editable?) {
        }

    }

    val passwordWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if(!p0.toString().isNullOrEmpty()) {
                _password.value = p0.toString()
            } else {
                _password.value = ""
            }
            checkRegister()

        }

        override fun afterTextChanged(p0: Editable?) {
        }

    }

    val passwordCheckWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            if(!p0.toString().isNullOrEmpty()) {
                if(_password.value!! == p0.toString()!!) {
                    _passwordCheck.value = p0.toString()
                    _pwWarning.value = false
                } else {
                    _pwWarning.value = true
                }
            } else {
                _passwordCheck.value = ""
                _pwWarning.value = false
            }
            checkRegister()
        }

        override fun afterTextChanged(p0: Editable?) {
        }

    }

    val emailCheckWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if (!p0.toString().isNullOrEmpty()) {
                if(android.util.Patterns.EMAIL_ADDRESS.matcher(p0).matches()) {
                    _emailWarning.value = false
                    _email.value = p0.toString()
                } else {
                    _emailWarning.value = true
                }
            } else {
                _email.value = ""
                _emailWarning.value = false
            }
            checkRegister()

        }

        override fun afterTextChanged(p0: Editable?) {
        }

    }

    fun checkRegister() {
        if(_email.value != "" && _password.value != "" && _passwordCheck.value != "" && _nickName.value != "") {
            _checking.value = !_pwWarning.value!! && !_emailWarning.value!!

        } else {
            _checking.value = false
        }

    }

    fun checkingRegister() {
        if (_checking.value!!) {
            val registerData : RegisterRequest = RegisterRequest(_email.value!!, _password.value!!, _nickName.value!!)
            socketRepository.postRegister(registerRequest = registerData,
                onSuccess = {
                    if (it.success) {
                        Timber.d("회원가입 성공")
                        authManager.token = it.data!!.token!!
                        _registerSuccess.value = true
                    }

                },
                onFailure = {
                    Timber.d("회원가입 실패")
                    _registerSuccess.value = false
                })
        }
    }


    /** UI 의 onDestroy 개념으로 생각하면 편할듯 */
    override fun onCleared() {
        super.onCleared()
    }
}
