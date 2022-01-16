package com.jdm.menunenaw.data.remote.repository

import androidx.paging.*
import com.jdm.menunenaw.data.remote.response.CategorySearchResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategorySearchRepo @Inject constructor(val kakaoRepo : KaKaoRepo) {

    fun getStoreList(y: String, x:String, radius: Int): Flow<PagingData<CategorySearchResponse.Document>>{
        return Pager(PagingConfig(pageSize = 15), initialKey = 1) {
            CategorySearchPagingSource(y, x, radius)
        }.flow
    }

    inner class CategorySearchPagingSource(val y: String, val x:String, val radius: Int) : PagingSource<Int,CategorySearchResponse.Document>(){
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CategorySearchResponse.Document> {
            try{
                val page = params.key ?: 1
                val response = kakaoRepo.getSearchCategory(y, x, radius, page)
                return LoadResult.Page(
                    response.documents,
                    if (page - 1 < 0) {
                        null
                    } else {
                        page - 1
                    },
                    if (response.meta.is_end) {
                        null
                    } else {
                        page + 1
                    })
            }catch (e: Exception){
                return LoadResult.Error(e)
            }
        }

        override fun getRefreshKey(state: PagingState<Int, CategorySearchResponse.Document>): Int {
            return 1
        }
    }
}