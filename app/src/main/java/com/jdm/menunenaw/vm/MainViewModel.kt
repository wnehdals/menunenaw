package com.jdm.menunenaw.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.location.Location
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.jdm.menunenaw.base.ViewModelBase
import com.jdm.menunenaw.data.MAX_STORE_COUNT
import com.jdm.menunenaw.data.remote.repository.KaKaoRepo
import com.jdm.menunenaw.data.remote.response.CategorySearchResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
@HiltViewModel
class MainViewModel @Inject constructor(private val kakaoRepo: KaKaoRepo): ViewModelBase(){
    private val TAG = MainViewModel::class.java.simpleName

    val locationRequestTimeInterval = (1000 * 10).toLong() // 10초
    val locationRequestDistanceInterval = 10.0f // 10 meters

    var location: Location? = null
    val queryFlow : MutableStateFlow<String> = MutableStateFlow("")
    val searchResult = queryFlow
        .debounce(SEARCH_DELAY_MILLIS)
        .mapLatest {
            if (it.length >= MIN_QUERY_LENGTH) {
                val request = kakaoRepo.getSearchLocation(it)
                Pair(DataType.REMOTE,request.documents)
            } else {
                Pair(DataType.DB, listOf())
                // todo : db, 최근 기록 가져오기.
            }
        }
        .catch {}
        .flowOn(Dispatchers.IO)
        .asLiveData()

    private val _searchStoreResult : MutableLiveData<List<CategorySearchResponse.Document>> = MutableLiveData()
    val searchStoreResult : LiveData<List<CategorySearchResponse.Document>> = _searchStoreResult

    init {

    }

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


    enum class DataType{
        REMOTE,
        DB
    }

    companion object{
        private const val SEARCH_DELAY_MILLIS = 500L
        private const val MIN_QUERY_LENGTH = 1
    }
}
