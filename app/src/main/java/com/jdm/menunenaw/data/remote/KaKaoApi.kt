package com.jdm.menunenaw.data.remote

import com.jdm.menunenaw.data.remote.response.LocationSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface KaKaoApi {
    @GET("v2/local/search/address.json")
    suspend fun getSearchLocation(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20,
        @Query("analyze_type") type: String = "similar"
    ) : LocationSearchResponse

}