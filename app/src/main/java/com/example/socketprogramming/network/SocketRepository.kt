package com.example.socketprogramming.network


import com.example.perfumeproject.data.response.KakaoLoginResponse
import com.example.socketprogramming.SocketApplication
import com.example.socketprogramming.data.request.KakaoLoginRequest
import com.example.socketprogramming.di.AuthManager
import com.example.socketprogramming.util.safeEnqueue
import timber.log.Timber
import javax.inject.Inject

class SocketRepository @Inject constructor(
    private val api: SocketService, private val authManager: AuthManager
) {
    init {
        val appContext = SocketApplication.getGlobalApplicationContext()
    }


    fun postKakaoLogin(
        kakaoLoginRequest: KakaoLoginRequest,
        onSuccess : (KakaoLoginResponse) -> Unit,
        onFailure : () -> Unit
    ) {
        api.postKakaoLogin(kakaoLoginRequest).safeEnqueue(
            onSuccess = { onSuccess(it.result!!)},
            onFailure = { onFailure()},
            onError = {onFailure()}
        )
    }

    fun settingUser(
        kakaoLoginRequest: KakaoLoginRequest,
        successAction: (() -> Unit)? = null,
        failAction: (() -> Unit)? = null
    ) {
        postKakaoLogin(
                kakaoLoginRequest = kakaoLoginRequest,
                onSuccess = {
                    authManager.serverToken = it.token
                    Timber.e("auth serverToken - ${authManager.serverToken}")
                    authManager.autoLogin = true
                    authManager.token = kakaoLoginRequest.kakaoToken
                    authManager.id = kakaoLoginRequest.userId!!
                    successAction?.let { it() }
                },
                onFailure = {
                    authManager.autoLogin = false
                    failAction?.let { it() }
                }
        )
    }
//
//    fun getSearchPerfume(
//        p_name : String,
//        onSuccess: (List<PerfumeData>?) -> Unit,
//        onFailure: () -> Unit
//    ) {
//        api.getSearchPerfume(p_name).safeEnqueue (
//                onSuccess = {onSuccess(it.result!!)},
//                onFailure = {onFailure()},
//                onError = {onFailure()}
//        )
//    }
//
//    fun putScrap(
//        scrapRequest: ScrapRequest,
//        onSuccess: () -> Unit,
//        onFailure: () -> Unit
//    ) {
//        api.putScrap(scrapRequest).safeEnqueue(
//            onSuccess = {onSuccess()},
//            onFailure = {onFailure()},
//            onError = {onFailure()}
//        )
//    }
//
//    fun getScrapData(
//        onSuccess: (List<PerfumeData>?) -> Unit,
//        onFailure: () -> Unit
//    ) {
//        api.getScrapData().safeEnqueue(
//            onSuccess = {onSuccess(it.result)},
//            onFailure = {onFailure()},
//            onError = {onFailure()}
//        )
//    }
//
//    fun getNewPerfumeList(
//        perfumeDescRequest: perfumeDescRequest,
//        onSuccess: (List<PerfumeData>?) -> Unit,
//        onFailure: () -> Unit
//    ) {
//        api.getNewPerfumeList(perfumeDescRequest).safeEnqueue(
//            onSuccess = {onSuccess(it.result!!)},
//            onFailure = {onFailure()},
//            onError = {onFailure()}
//        )
//    }
//
//    fun postBasedPerfume(
//        scrapRequest: ScrapRequest,
//        onSuccess: (List<PerfumeData>?) -> Unit,
//        onFailure: () -> Unit
//    ) {
//        api.postBasedPerfume(scrapRequest).safeEnqueue(
//            onSuccess = {onSuccess(it.result!!)},
//            onFailure = {onFailure()},
//            onError = {onFailure()}
//        )
//    }
}
