package com.example.socketprogramming.ui

import android.os.Bundle
import com.example.perfumeproject.ui.base.BaseViewModel
import com.example.socketprogramming.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.socketprogramming.R
import com.example.socketprogramming.databinding.ActivitySplashBinding
import com.example.socketprogramming.ui.base.BaseActivity
import com.example.socketprogramming.ui.login.LoginActivity
import com.example.socketprogramming.util.startActivity


@AndroidEntryPoint
class SplashActivity(
    override val viewModel: BaseViewModel? = null
) : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uiScope.launch {
            delay(DURATION)
            checkToken()
        }
    }

    private fun checkToken() {
      if (authManager.autoLogin) {
          startActivity(HomeActivity::class, false)

      } else {
          startActivity(LoginActivity::class, true)
      }
    }



    companion object {
        private const val DURATION: Long = 1500
    }
}