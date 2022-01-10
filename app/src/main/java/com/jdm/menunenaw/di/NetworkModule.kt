package com.jdm.menunenaw.di

import android.content.Context
import com.jdm.menunenaw.MenunenawApp
import com.jdm.menunenaw.R
import com.jdm.menunenaw.data.remote.KaKaoApi
import com.jdm.menunenaw.utils.KakaoAuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AUTH


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    val httpLogginInterceptor = HttpLoggingInterceptor().apply {
        level = if (MenunenawApp.instance.isApplicationDebug) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.HEADERS
        }
    }
    @AUTH
    @Provides
    @Singleton
    fun provideAuthInterceptor(@ActivityContext context: Context): Interceptor {
        return Interceptor { chain: Interceptor.Chain ->

            val newRequest: Request = chain.request().newBuilder()
                .addHeader("Authorization", "KakaoAK ${context.resources.getString(R.string.KAKAO_HEADER_KEY)}")
                .build()
            chain.proceed(newRequest)
        }
    }

    @AUTH
    @Provides
    @Singleton
    fun provideAuthOkHttpClient(@ApplicationContext appContext: Context): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(10, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(15, TimeUnit.SECONDS)
            addInterceptor(httpLogginInterceptor)
            addInterceptor(KakaoAuthInterceptor(appContext))
        }.build()
    }
    @AUTH
    @Provides
    @Singleton
    fun provideAuthRetrofit(@AUTH okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://dapi.kakao.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @AUTH
    @Provides
    @Singleton
    fun provideAuthApi(@AUTH retrofit: Retrofit): KaKaoApi {
        return retrofit.create(KaKaoApi::class.java)
    }
}
