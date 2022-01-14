package com.jdm.menunenaw.data.remote.response

data class LocationInfoResponse(
    val documents: List<Document>,
    val meta: Meta
){

    data class Document(
        val address: Address,
        val road_address: RoadAddress
    )

    data class Address(
        val address_name: String,
        val main_address_no: String,
        val mountain_yn: String,
        val region_1depth_name: String,
        val region_2depth_name: String,
        val region_3depth_name: String,
        val sub_address_no: String,
        val zip_code: String
    )

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
        val zone_no: String
    )

    data class Meta(val total_count: Int)
}
