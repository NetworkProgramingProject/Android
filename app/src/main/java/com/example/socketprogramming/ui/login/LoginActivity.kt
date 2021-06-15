package com.example.socketprogramming.ui.login

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.socketprogramming.BR
import com.example.socketprogramming.R
import com.example.socketprogramming.databinding.ActivityLoginBinding
import com.example.socketprogramming.ui.base.BaseActivity
import com.example.socketprogramming.ui.home.HomeActivity
import com.example.socketprogramming.ui.register.RegisterActivity
import com.example.socketprogramming.util.startActivity
import com.example.socketprogramming.util.toast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    override val viewModel: LoginViewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.vm, viewModel)

        viewModel.register.observe(this, Observer {
            if(it) {
               startActivity(RegisterActivity::class, false)
            }
        })

        viewModel.loginSuccess.observe(this, Observer {
            if(it) {
                toast("로그인 되었습니다 :)")
                startActivity(HomeActivity::class, true)
            }
        })

        viewModel.loginFail.observe(this, Observer {
            if(it) {
                toast("${viewModel.msg.value}")
            }
        })

    }

    override fun onBackPressed() {
        if(viewModel.login.value == true) {
            viewModel.onBackPressed()
        } else {
            finish()
        }
    }

}
