package com.example.socketprogramming.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.socketprogramming.BR
import com.example.socketprogramming.R
import com.example.socketprogramming.databinding.ActivityRegisterBinding
import com.example.socketprogramming.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegisterActivity : BaseActivity<ActivityRegisterBinding>(R.layout.activity_register) {
    override val viewModel: RegisterViewModel by viewModels<RegisterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.vm, viewModel)
        binding.apply {

        }
    }

}