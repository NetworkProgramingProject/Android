package com.example.socketprogramming.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.socketprogramming.BR
import com.example.socketprogramming.R
import com.example.socketprogramming.databinding.ActivityRegisterBinding
import com.example.socketprogramming.ui.base.BaseActivity
import com.example.socketprogramming.ui.home.HomeActivity
import com.example.socketprogramming.ui.login.LoginActivity
import com.example.socketprogramming.util.startActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegisterActivity : BaseActivity<ActivityRegisterBinding>(R.layout.activity_register) {
    override val viewModel: RegisterViewModel by viewModels<RegisterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.vm, viewModel)

        viewModel.registerSuccess.observe(this, Observer {
            if(it) {
                startActivity(LoginActivity::class, false)
            }
        })
    }

}