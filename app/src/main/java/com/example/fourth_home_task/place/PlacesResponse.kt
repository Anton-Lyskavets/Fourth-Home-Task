package com.example.fourth_home_task.place

import com.google.android.gms.maps.model.LatLng

data class PlaceResponse(
    val install_place: String,
    val address_type: String,
    val address: String,
    val house: String,
    val gps_x: String,
    val gps_y: String,
    val work_time: String
)

fun PlaceResponse.toPlace(): Place = Place(
    install_place = install_place,
    latLng = LatLng(gps_x.toDouble(), gps_y.toDouble()),
    address = "$address_type $address, $house",
    work_time = work_time
)
