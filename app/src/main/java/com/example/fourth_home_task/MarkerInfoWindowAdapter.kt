package com.example.fourth_home_task

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.example.fourth_home_task.place.Place

class MarkerInfoWindowAdapter(
    private val context: Context
) : GoogleMap.InfoWindowAdapter {
    @SuppressLint("InflateParams", "SetTextI18n")
    override fun getInfoContents(marker: Marker): View? {
        val place = marker.tag as? Place ?: return null
        val view = LayoutInflater.from(context).inflate(
            R.layout.marker_info_contents, null
        )
        view.findViewById<TextView>(
            R.id.text_view_title
        ).text = place.install_place
        view.findViewById<TextView>(
            R.id.text_view_address
        ).text = place.address
        view.findViewById<TextView>(
            R.id.text_view_work_time
        ).text = place.work_time
        return view
    }

    override fun getInfoWindow(marker: Marker): View? {
        return null
    }
}
