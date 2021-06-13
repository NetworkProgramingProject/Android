package com.example.socketprogramming.network



import com.example.socketprogramming.data.request.AuctionPriceRequest
import com.example.socketprogramming.data.request.LoginRequest
import com.example.socketprogramming.data.request.ProductRequest
import com.example.socketprogramming.data.request.RegisterRequest
import com.example.socketprogramming.data.response.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

/**
 * 실제 서비스에서 사용하는 Retrofit2 인터페이스
 *
 */
interface SocketService {

    @POST("auth/join")
    fun postRegister(
        @Body registerRequest: RegisterRequest
    ): Call<RegisterResponse>

    @POST("auth/login")
    fun postLogin(
        @Body loginRequest: LoginRequest
    ) : Call<LoginResponse>

    @Multipart
    @Headers("Content-Type: multipart/form-data")
    @POST("goods")
    fun postRegisterProduct(
        @Part("title") title : String,
        @Part("desc") desc : String,
        @Part("min_price") minPrice : Int,
        @Part("img") img : MultipartBody.Part?
    ) : Call<LoginResponse>


    @GET("goods")
    fun getProductList(
    ) : Call<SocketBaseResponse<List<ProductData>>>


    @POST("goods/{id}/bid")
    fun postAuctionPrice(
        @Path("id") goodsId : Int,
        @Body auctionPriceRequest: AuctionPriceRequest
    ) : Call<LoginResponse>


    @GET("mypage")
    fun getUserData() : Call<SocketBaseResponse<UserResponse>>
}
