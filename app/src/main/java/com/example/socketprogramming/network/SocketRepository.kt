package com.example.socketprogramming.network


import com.example.socketprogramming.SocketApplication
import com.example.socketprogramming.data.request.AuctionPriceRequest
import com.example.socketprogramming.data.request.LoginRequest
import com.example.socketprogramming.data.request.ProductRequest
import com.example.socketprogramming.data.request.RegisterRequest
import com.example.socketprogramming.data.response.LoginResponse
import com.example.socketprogramming.data.response.ProductData
import com.example.socketprogramming.data.response.RegisterResponse
import com.example.socketprogramming.data.response.SocketAuctionResponse
import com.example.socketprogramming.di.AuthManager
import com.example.socketprogramming.util.safeEnqueue
import okhttp3.MultipartBody
import javax.inject.Inject

class SocketRepository @Inject constructor(
    private val api: SocketService, private val authManager: AuthManager, private val socketManager: SocketManager
) {
    init {
        val appContext = SocketApplication.getGlobalApplicationContext()
    }


    fun postRegister(
        registerRequest: RegisterRequest,
        onSuccess: (RegisterResponse) -> Unit,
        onFailure: () -> Unit
    ) {
        api.postRegister(registerRequest).safeEnqueue(
            onSuccess = { onSuccess(it) },
            onFailure = { onFailure() },
            onError = { onFailure() }
        )
    }

    fun postLogin(
        loginRequest: LoginRequest,
        onSuccess: (LoginResponse) -> Unit,
        onFailure: () -> Unit
    ) {
        api.postLogin(loginRequest).safeEnqueue(
            onSuccess = { onSuccess(it) },
            onFailure = { onFailure() },
            onError = { onFailure() }
        )
    }

    fun postRegisterProduct(
        title : String,
        desc : String,
        minPrice : Int,
        img : MultipartBody.Part,
        onSuccess: (LoginResponse) -> Unit,
        onFailure: () -> Unit
    ) {
        api.postRegisterProduct(title, desc, minPrice, img).safeEnqueue(
            onSuccess = { onSuccess(it) },
            onFailure = { onFailure() },
            onError = { onFailure() }
        )
    }

    fun getProductList(
        onSuccess: (List<ProductData>) -> Unit,
        onFailure: () -> Unit
    ) {
        api.getProductList().safeEnqueue(
            onSuccess = { onSuccess(it.result!!) },
            onFailure = { onFailure() },
            onError = { onFailure() }
        )
    }

    fun socketJoin(
        goodsId : Int,
        onData : (Array<Any>) -> Unit
    ) {
        socketManager.joinRoom(goodsId, onData = {
            onData(it)
        })
    }

    fun postAuctionPrice(
        goodsId: Int,
        auctionPriceRequest: AuctionPriceRequest,
        onSuccess: (LoginResponse) -> Unit,
        onFailure: () -> Unit
    ) {
        api.postAuctionPrice(goodsId, auctionPriceRequest).safeEnqueue (
            onSuccess = { onSuccess(it) },
            onFailure = { onFailure() },
            onError = { onFailure() }
        )
    }



//    fun postKakaoLogin(
//        kakaoLoginRequest: KakaoLoginRequest,
//        onSuccess : (KakaoLoginResponse) -> Unit,
//        onFailure : () -> Unit
//    ) {
//        api.postKakaoLogin(kakaoLoginRequest).safeEnqueue(
//            onSuccess = { onSuccess(it.result!!)},
//            onFailure = { onFailure()},
//            onError = {onFailure()}
//        )
//    }


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
