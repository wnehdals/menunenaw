package com.jdm.menunenaw.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jdm.menunenaw.base.ViewModelBase
import com.jdm.menunenaw.data.MAX_STORE_COUNT
import com.jdm.menunenaw.data.remote.repository.KaKaoRepo
import com.jdm.menunenaw.data.remote.response.CategorySearchResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class StoreSelectViewModel@Inject constructor(private val kakaoRepo: KaKaoRepo): ViewModelBase(){

    val allSelectLiveData = MutableLiveData(true)
    val activeNextLiveData = MutableLiveData(true)

    private val _searchStoreResult : MutableLiveData<List<CategorySearchResponse.Document>> = MutableLiveData()
    val searchStoreResult : LiveData<List<CategorySearchResponse.Document>> = _searchStoreResult

    /* 근처 음식점 리스트 모두 가져오기 */
    fun requestSearchCategoryAllList(latitude : Double, longitude : Double, radius:Int){
        viewModelScope.launch(exceptionHandler) {
            withContext(Dispatchers.IO) {
                val list = mutableListOf<CategorySearchResponse.Document>()
                for (page in 1..MAX_STORE_COUNT) {
                    val result = kakaoRepo.getSearchCategory(
                        latitude.toString(),
                        longitude.toString(),
                        radius,
                        page
                    )
                    list.addAll(result.documents)
                    if (result.meta.is_end) {
                        break
                    }
                }
                _searchStoreResult.postValue(list)
            }
        }
    }
}