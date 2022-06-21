package com.example.fourth_home_task.place

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class Place(
    val install_place: String,
    val latLng: LatLng,
    val address: String,
    val work_time: String
) : ClusterItem {
    override fun getPosition(): LatLng =
        latLng

    override fun getTitle(): String =
        install_place

    override fun getSnippet(): String =
        address
}
