package com.jdm.menunenaw.vm

import androidx.lifecycle.asLiveData
import com.jdm.menunenaw.base.ViewModelBase
import com.jdm.menunenaw.data.remote.repository.KaKaoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
@HiltViewModel
class MainViewModel @Inject constructor(private val kakaoRepo: KaKaoRepo): ViewModelBase(){
    private val TAG = MainViewModel::class.java.simpleName
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

    init {

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