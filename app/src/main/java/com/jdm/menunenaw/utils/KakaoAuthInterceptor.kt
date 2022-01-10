package com.jdm.menunenaw.utils

import android.content.Context
import com.jdm.menunenaw.R
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject
class KakaoAuthInterceptor @Inject constructor(
    private val context: Context
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest: Request = chain.request().newBuilder()
            .addHeader(
                "Authorization",
                "KakaoAK ${context.resources.getString(R.string.KAKAO_HEADER_KEY)}"
            )
            .build()
        return chain.proceed(newRequest)
    }

}
