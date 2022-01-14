package com.jdm.menunenaw.data.remote.repository

import com.jdm.menunenaw.data.remote.KaKaoApi
import retrofit2.http.Query
import javax.inject.Inject

class KaKaoRepo @Inject constructor(private val api : KaKaoApi) {
    suspend fun getSearchLocation(query: String, page: Int = 1) = api.getSearchLocation(query, page)
    suspend fun getLocationInfo(latitude: String, longitude: String) = api.getLocationInfo(latitude, longitude)
    suspend fun getSearchCategory(latitude: String, longitude: String, radius: Int) = api.getSearchCategory(latitude, longitude, radius)
}