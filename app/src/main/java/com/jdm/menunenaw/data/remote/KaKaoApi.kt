package com.jdm.menunenaw.data.remote

import com.jdm.menunenaw.data.remote.response.CategorySearchResponse
import com.jdm.menunenaw.data.remote.response.LocationInfoResponse
import com.jdm.menunenaw.data.remote.response.LocationSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface KaKaoApi {

    /** 주소 검색
     * @param query : 검색을 원하는 질의어
     * @param page : 결과 페이지 번호, 1-45 사이, 기본 값 1
     * @param size : 한 페이지에 보여질 문서의 개수, 1-30 사이, 기본 값 10
     * @param type :검색 결과 제공 방식 (similar or exact)
     * 참고 url : https://developers.kakao.com/tool/rest-api/open/get/v2-local-search-address.%7Bformat%7D
     * */
    @GET("v2/local/search/address.json")
    suspend fun getSearchLocation(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20,
        @Query("analyze_type") type: String = "similar"
    ) : LocationSearchResponse

    /** 좌표로 주소 정보 가져오기
     * @param latitude(y) 위도
     * @param longitude(x) 경도
     * @param coord x, y 로 입력되는 값에 대한 좌표 체계
     * 참고 url : https://developers.kakao.com/tool/rest-api/open/get/v2-local-geo-coord2address.%7Bformat%7D
     * */
    @GET("/v2/local/geo/coord2address.json")
    suspend fun getLocationInfo(
        @Query("y") latitude: String,
        @Query("x") longitude: String,
        @Query("input_coord") coord: String = "WGS84"
        ) : LocationInfoResponse

    /** 카테고리로 장소 검색
     * @param latitude(y) 위도
     * @param longitude(x) 경도
     * @param radius 반경 meter단위, 0 ~ 20,000
     * @param category 카테고리 그룹 코드. FD6 = 음식점.
     * @param page : 결과 페이지 번호, 1-45 사이, 기본 값 1
     * @param size : 한 페이지에 보여질 문서의 개수, 1-15 사이, 기본 값 15
     * @param sort : 결과 정렬 순서
     * 참고 url : https://developers.kakao.com/tool/rest-api/open/get/v2-local-search-category.%7Bformat%7D
     * */
    @GET("/v2/local/search/category.json")
    suspend fun getSearchCategory(
        @Query("y") latitude: String,
        @Query("x") longitude: String,
        @Query("radius") radius: Int,
        @Query("rect") rect: String = "",
        @Query("category_group_code") category: String = "FD6",
        @Query("page ") page: Int = 1,
        @Query("size") size: Int = 15,
        @Query("sort") sort: String = "accuracy"
    ): CategorySearchResponse
}