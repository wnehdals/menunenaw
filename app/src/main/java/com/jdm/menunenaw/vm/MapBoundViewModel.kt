package com.jdm.menunenaw.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jdm.menunenaw.base.ViewModelBase
import com.jdm.menunenaw.data.remote.repository.KaKaoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapBoundViewModel@Inject constructor(private val kakaoRepo: KaKaoRepo): ViewModelBase(){

    private val _locationNameLiveData = MutableLiveData("")
    val locationNameLiveData : LiveData<String> get() = _locationNameLiveData

    private val _searchCountLiveData = MutableLiveData(0)
    val searchResultLiveData : LiveData<Int> get() = _searchCountLiveData

    fun setLocationName(location : String){
        _locationNameLiveData.value = location
    }

    /* 위도, 경도로 주소 찾기 */
    fun getLocationInfo(latitude : Double, longitude : Double){
        viewModelScope.launch(exceptionHandler) {
            kakaoRepo.getLocationInfo(latitude.toString(), longitude.toString()).let {
                if (it.documents.isNotEmpty()) {
                    _locationNameLiveData.value =it.documents[0].address.address_name
                }
            }
        }
    }

    /* 근처 음식점 개수 */
    fun getSearchCategoryCount(
        latitude: Double,
        longitude: Double,
        radius: Int,
        page : Int
    ) {
        viewModelScope.launch(exceptionHandler) {
            kakaoRepo.getSearchCategory(latitude.toString(), longitude.toString(), radius, page)
                .let {
                    _searchCountLiveData.value = it.meta.total_count
                }
        }
    }
}