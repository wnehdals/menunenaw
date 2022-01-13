package com.jdm.menunenaw.vm

import androidx.lifecycle.MutableLiveData
import com.jdm.menunenaw.base.ViewModelBase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RouletteViewModel @Inject constructor() : ViewModelBase(){
    private val TAG = RouletteViewModel::class.java.simpleName

//    /** 룰렛 결과 */
//    private val _result = MutableLiveData<MutableList<String>>()
//    val result get() = _result

    /** 룰렛 돌아가는 중 현재 가리키고 있는 데이터 Name */
    private val _currentLabel = MutableLiveData<String>()
    val currentLabel get() = _currentLabel

    fun setCurrentLabel(label: String) {
        _currentLabel.postValue(label)
    }
}