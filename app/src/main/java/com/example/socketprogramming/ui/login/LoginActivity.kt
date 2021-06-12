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
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    override val viewModel: LoginViewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.vm, viewModel)

        viewModel.register.observe(this, Observer {
            if(it) {
               startActivity(RegisterActivity::class, true)
            }
        })

        viewModel.loginSuccess.observe(this, Observer {
            if(it) {
                startActivity(HomeActivity::class, true)
            }
        })

    }

    override fun onBackPressed() {
        viewModel.login.observe(this, Observer {
            if(it) {
                viewModel.onBackPressed()
            }
        })
    }

}
