package com.example.socketprogramming.network



import com.example.perfumeproject.data.response.KakaoLoginResponse
import com.example.socketprogramming.data.request.KakaoLoginRequest
import com.example.socketprogramming.data.response.SocketBaseResponse
import retrofit2.Call
import retrofit2.http.*

/**
 * 실제 서비스에서 사용하는 Retrofit2 인터페이스
 *
 */
interface SocketService {

    @POST("auth/login")
    fun postKakaoLogin(
            @Body kakaoLoginRequest: KakaoLoginRequest
    ): Call<SocketBaseResponse<KakaoLoginResponse>>
//
//    @GET("perfume/search")
//    fun getSearchPerfume(
//            @Query("p_name") searchKeyword : String
//    ) : Call<PerfumeResponse<List<PerfumeData>>>
//
//    @PUT("scrap")
//    fun putScrap(
//        @Body scrapRequest: ScrapRequest
//    ) : Call<PerfumeResponse<Unit>>
//
//    @GET("scrap")
//    fun getScrapData() : Call<PerfumeResponse<List<PerfumeData>?>>
//
//    @POST("perfume/recommend/new")
//    fun getNewPerfumeList(
//        @Body perfumeDescRequest: perfumeDescRequest
//    ) : Call<PerfumeResponse<List<PerfumeData>>>
//
//    @POST ("perfume/recommend/based")
//    fun postBasedPerfume(
//        @Body scrapRequest: ScrapRequest
//    ) : Call<PerfumeResponse<List<PerfumeData>>>
}
