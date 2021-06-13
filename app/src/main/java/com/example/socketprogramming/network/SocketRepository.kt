package com.example.socketprogramming.network


import com.example.socketprogramming.SocketApplication
import com.example.socketprogramming.data.request.AuctionPriceRequest
import com.example.socketprogramming.data.request.LoginRequest
import com.example.socketprogramming.data.request.ProductRequest
import com.example.socketprogramming.data.request.RegisterRequest
import com.example.socketprogramming.data.response.*
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

    fun getUserData(
        onSuccess: (UserResponse) -> Unit,
        onFailure: () -> Unit
    ) {
        api.getUserData().safeEnqueue (
            onSuccess = { onSuccess(it.result!!) },
            onFailure = { onFailure() },
            onError = { onFailure() }
        )
    }


}
