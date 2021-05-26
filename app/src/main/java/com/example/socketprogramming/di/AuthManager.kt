package com.example.socketprogramming.di

import com.example.socketprogramming.data.AuthConstant.AUTO_LOGIN_KEY
import com.example.socketprogramming.data.AuthConstant.EXPIRE_KEY
import com.example.socketprogramming.data.AuthConstant.ID
import com.example.socketprogramming.data.AuthConstant.SEARCH
import com.example.socketprogramming.data.AuthConstant.SERVER_TOKEN
import com.example.socketprogramming.data.AuthConstant.TOKEN_KEY
import com.example.socketprogramming.util.SharedPrefs
import javax.inject.Inject


class AuthManager @Inject constructor(val sharedPrefs: SharedPrefs) {
    var token: String
        get() {
            return sharedPrefs[TOKEN_KEY, ""] ?: ""
        }
        set(value) {
            sharedPrefs[TOKEN_KEY] = value
        }

    var serverToken: String
        get() {
            return sharedPrefs[SERVER_TOKEN, ""] ?: ""
        }
        set(value) {
            sharedPrefs[SERVER_TOKEN] = value
        }

    var autoLogin: Boolean
        get() {
            return sharedPrefs[AUTO_LOGIN_KEY, false] ?: false
        }
        set(value) {
            sharedPrefs[AUTO_LOGIN_KEY] = value
        }

    var expire: Long
        get() {
            return sharedPrefs[EXPIRE_KEY, 0] ?: 0
        }
        set(value) {
            sharedPrefs[EXPIRE_KEY] = value
        }

    var id: Long
        get() {
            return sharedPrefs[ID, 0] ?: 0
        }
        set(value) {
            sharedPrefs[ID] = value
        }

    var search: Boolean
        get() {
            return sharedPrefs[SEARCH, false] ?: false
        }
        set(value) {
            sharedPrefs[SEARCH] = value
        }

}
