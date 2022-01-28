package com.jdm.menunenaw.vm

import androidx.lifecycle.ViewModel
import com.jdm.menunenaw.base.ViewModelBase
import com.jdm.menunenaw.data.model.FoodCategory
import com.jdm.menunenaw.data.remote.repository.KaKaoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FoodCategorySelectViewModel @Inject constructor(): ViewModel() {

    fun makeRouletteDataArray(data: List<FoodCategory>) : Array<String> {
        val resultArray = mutableListOf<String>()
        data.forEach {
            resultArray.addAll(
                it.subCategoryList.filterIndexed { index, s ->
                    it.selectedList?.get(index) ?: true
                }
            )
        }
        return resultArray.toTypedArray()
    }
}
