package com.example.socketprogramming

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber


@HiltAndroidApp
class SocketApplication : Application() {
    companion object {
        var appContext : Context? = null

        fun getGlobalApplicationContext(): Context {
            checkNotNull(appContext) { "This Application does not inherit com.kakao.GlobalApplication" }
            return appContext!!
        }

        fun getGlobalAppApplication() : SocketApplication {
            checkNotNull(appContext) { "This Application does not inherit com.kakao.GlobalApplication" }
            return appContext!! as SocketApplication
        }
    }
    override fun onCreate() {
        super.onCreate()
        appContext = this
        initTimber()
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

}
