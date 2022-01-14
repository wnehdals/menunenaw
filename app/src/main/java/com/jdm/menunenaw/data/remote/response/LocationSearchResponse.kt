package com.jdm.menunenaw.data.remote.response

data class LocationSearchResponse(
    val documents: List<Document>,
    val meta: Meta
) {

    data class Document(
        val address: Address,
        val address_name: String,
        val address_type: String,
        val road_address: RoadAddress,
        val x: String,
        val y: String
    ) {
        override fun equals(other: Any?): Boolean {
            return if(other != null && other is Document){
                other.x == this.x && other.y == this.y
            }else{
                false
            }
        }
    }

    data class Address(
        val address_name: String,
        val b_code: String,
        val h_code: String,
        val main_address_no: String,
        val mountain_yn: String,
        val region_1depth_name: String,
        val region_2depth_name: String,
        val region_3depth_h_name: String,
        val region_3depth_name: String,
        val sub_address_no: String,
        val x: String,
        val y: String
    ){
        override fun equals(other: Any?): Boolean {
            return if(other != null && other is Address){
                other.b_code == this.b_code && other.h_code == this.h_code
            }else{
                false
            }
        }
    }

    data class RoadAddress(
        val address_name: String,
        val building_name: String,
        val main_building_no: String,
        val region_1depth_name: String,
        val region_2depth_name: String,
        val region_3depth_name: String,
        val road_name: String,
        val sub_building_no: String,
        val underground_yn: String,
        val x: String,
        val y: String,
        val zone_no: String
    )

    data class Meta(
        val is_end: Boolean,
        val pageable_count: Int,
        val total_count: Int
    )
}