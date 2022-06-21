package com.example.fourth_home_task.network

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import com.squareup.moshi.Json

data class BankATM(
    @Json(name = "install_place") val installPlace: String,
    @Json(name = "address_type") val addressType: String,
    val address: String,
    val house: String,
    @Json(name = "gps_x") val gpsX: String,
    @Json(name = "gps_y") val gpsY: String,
    @Json(name = "work_time") val workTime: String,
//    val id: String,
//    val area: String,
//    @Json(name = "city_type") val cityType: String,
//    val city: String,
//    @Json(name = "install_place_full") val installPlaceFull: String,
//    @Json(name = "work_time_full") val workTimeFull: String,
//    @Json(name = "ATM_type") val ATMType: String,
//    @Json(name = "ATM_error") val ATMError: String,
//    val currency: String,
//    @Json(name = "cash_in") val cashIn: String,
//    @Json(name = "ATM_printer") val ATMPrinter: String
) : ClusterItem {
    override fun getPosition(): LatLng =
        LatLng(gpsX.toDouble(), gpsY.toDouble())

    override fun getTitle(): String =
        installPlace

    override fun getSnippet(): String =
        "$addressType $address, $house. $workTime "
}