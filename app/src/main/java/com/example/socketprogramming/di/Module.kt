package com.example.socketprogramming.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.perfumeproject.ui.base.BaseViewModel
import com.example.socketprogramming.BuildConfig
import com.example.socketprogramming.SocketApplication
import com.example.socketprogramming.network.SocketManager
import com.example.socketprogramming.network.SocketRepository
import com.example.socketprogramming.network.SocketService
import com.example.socketprogramming.util.SharedPrefs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


const val CONNECT_TIMEOUT = 15.toLong()
const val WRITE_TIMEOUT = 15.toLong()
const val READ_TIMEOUT = 15.toLong()

const val BASE_URL = "http://3.37.7.7:3000/"

/**
 * 코루틴을 활용하여 HTTP 요청을 보낼 시 활용하는 로직
 * 코루틴을 활용할 경우, onFailure 에서 보내는 exception 내용에 따라 로직 작업을 수행한다.
 */
suspend fun <T> Call<T>.send(): Response<T> = suspendCoroutine {
    this.enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            it.resume(response)
        }

        override fun onFailure(call: Call<T>, throwable: Throwable) {
            it.resumeWithException(throwable)
        }
    })
}

@Module
@InstallIn(ActivityComponent::class)
class ActivityModule {
    @Provides
    fun provideViewModelFactory(
        socketRepository: SocketRepository
    ): ViewModelProvider.AndroidViewModelFactory = ViewModelFactoryImpl(
        SocketApplication.getGlobalAppApplication(), socketRepository
    )

    /**
     * ViewModelFactory 구현체 (impl) 를 만드는 클래스
     */
    class ViewModelFactoryImpl(
        val socketApplication: SocketApplication,
        val socketRepository: SocketRepository
    ) : ViewModelProvider.AndroidViewModelFactory(socketApplication) {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return BaseViewModel(socketRepository) as T
        }
    }
}


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpCache(): Cache {
        // 10MB
        val cacheSize = 10 * 1024 * 1024L
        return Cache(SocketApplication.getGlobalApplicationContext().cacheDir, cacheSize)
    }

    /**
     * 커스텀 interceptor
     * Firebase Crashlytic 로깅 로직을 넣을 예정이며 카카오 token 체크가 필요할 시 아래 함수를 활용한다.
     */
    @Provides
    @Singleton
    fun provideWinePickInterceptor(authManager: AuthManager): Interceptor {
        return Interceptor { chain: Interceptor.Chain ->
            val request = chain.request()
            var newUrl = request.url.toString()
            val builder = chain.request().newBuilder()
                .url(newUrl)

            if (newUrl.contains("/goods")) {
                return@Interceptor chain.proceed(chain.request().newBuilder().apply {
                    addHeader("token", authManager.token)
                    url(newUrl)
                }.build())
            }

            if (newUrl.contains("/mypage")) {
                return@Interceptor chain.proceed(chain.request().newBuilder().apply {
                    addHeader("token", authManager.token)
                    url(newUrl)
                }.build())
            }

            return@Interceptor chain.proceed(builder.build())
        }
    }

    /** HttpClient 객체를 생성하는 Provider 함수이다. */
    @Provides
    @Singleton
    fun provideHttpClient(okHttpCache: Cache, winePickInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(okHttpCache)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(winePickInterceptor)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    /** Retrofit 객체를 생성하는 Provider 함수이다. */
    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): SocketService {
        return retrofit.create(SocketService::class.java)
    }

    @Provides
    @Singleton
    fun provideSocketService(): SocketManager {
        return SocketManager()
    }
}

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    @Singleton
    fun provideSharedPrefs(): SharedPrefs {
        return SharedPrefs()
    }
}

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Provides
    @Singleton
    fun provideAuthManager(sharedPrefs: SharedPrefs): AuthManager {
        return AuthManager(sharedPrefs)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun providePerfumeRepository(
            socketService: SocketService,
            authManager: AuthManager,
            socketManager: SocketManager
    ): SocketRepository {
        return SocketRepository(socketService, authManager, socketManager)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

}



