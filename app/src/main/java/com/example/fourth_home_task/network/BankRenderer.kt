package com.example.fourth_home_task.network

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.fourth_home_task.BitmapHelper
import com.example.fourth_home_task.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer

class BankRenderer(
    private val context: Context,
    map: GoogleMap,
    clusterManager: ClusterManager<BankATM>
) : DefaultClusterRenderer<BankATM>(context, map, clusterManager) {
    private val bicycleIcon: BitmapDescriptor by lazy {
        val color = ContextCompat.getColor(
            context,
            R.color.pink_500
        )
        BitmapHelper.vectorToBitmap(
            context,
            R.drawable.baseline_savings_24,
            color
        )
    }

    override fun onBeforeClusterItemRendered(
        item: BankATM,
        markerOptions: MarkerOptions
    ) {
        markerOptions.title(item.installPlace)
            .position(LatLng(item.gpsX.toDouble(), item.gpsY.toDouble()))
            .icon(bicycleIcon)
    }

    override fun onClusterItemRendered(clusterItem: BankATM, marker: Marker) {
        marker.tag = clusterItem
    }
}