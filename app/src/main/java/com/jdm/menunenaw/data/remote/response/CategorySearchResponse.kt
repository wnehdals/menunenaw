package com.jdm.menunenaw.data.remote.response

import androidx.lifecycle.MutableLiveData

data class CategorySearchResponse(
    val documents: List<Document>,
    val meta: Meta
){
    data class Meta(
        val is_end: Boolean,
        val pageable_count: Int,
        val same_name: Any,
        val total_count: Int
    )

    data class Document(
        val address_name: String,
        val category_group_code: String,
        val category_group_name: String,
        val category_name: String,
        val distance: String,
        val id: String,
        val phone: String,
        val place_name: String,
        val place_url: String,
        val road_address_name: String,
        val x: String,
        val y: String
    ){

        var select = true
        var selectChangeListener : (()->Unit)? = null
        fun updateSelect(newSelect : Boolean){
            select = newSelect
            selectChangeListener?.invoke()
        }


        override fun equals(other: Any?): Boolean {
            return if( other is Document){
                this.id == other.id
                        && this.category_group_code == other.category_group_code
                        && this.y == other.y
                        && this.x == other.x
                        && this.select == other.select
            }else{
                false
            }
        }

        fun getSubCategory() = try {category_name.substring(
            0.coerceAtLeast(category_name.lastIndexOf('>') + 1),
            category_name.length)} catch (e: Exception){""}

        fun getDistanceText() = "${distance}m"
    }
}