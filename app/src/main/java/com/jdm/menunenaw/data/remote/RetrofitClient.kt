package com.jdm.menunenaw.data.remote

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Headers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitClient {
    private val TAG = RetrofitClient::class.java.simpleName

    private val builder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(getClient())

    private fun getClient(headers : Map<String,String>? = null) : OkHttpClient{
        val builder = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder().apply {
                    headers?.forEach{ addHeader(it.key,it.value)}
                }.build()
                Log.i(TAG, " request url : ${newRequest.url()}, header : ${newRequest.headers() }")
                chain.proceed(newRequest)
            }
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideKakaoRetrofitApi() : KaKaoApi{
        return builder
            .baseUrl("https://dapi.kakao.com/")
            .client(getClient(mapOf("Authorization" to "KakaoAK 3b92b7bd0367e095d91e74e244da25a2")))
            .build()
            .create(KaKaoApi::class.java)
    }
}