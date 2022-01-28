package com.jdm.menunenaw.data.model

data class FoodCategory(
    val mainCategoryTitle: String,
    val subCategoryList: Array<String>,
    var selectedList: Array<Boolean>? = null
) {
    init {
        selectedList = Array(subCategoryList.size) {
            return@Array true
        }
    }
}



